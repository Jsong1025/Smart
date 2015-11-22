package com.smart.helper;

import com.smart.util.CollectionUtil;
import com.smart.util.ProperUtil;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 *
 * @author jsong
 */
public final class DatabaseHelper {


    private static final Logger LOGGER ;
    private static final QueryRunner QUERY_RUNNER;
    private static final ThreadLocal<Connection> CONNECTION_HOLDED;
    private static final BasicDataSource DATA_SOURCE;

    static {
        LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);
        QUERY_RUNNER = new QueryRunner();
        CONNECTION_HOLDED = new ThreadLocal<>();
        DATA_SOURCE = new BasicDataSource();

        Properties conf = ProperUtil.loadProps("config.properties");
        String driver = conf.getProperty("jdbc.driver");
        String url = conf.getProperty("jdbc.url");
        String username = conf.getProperty("jdbc.username");
        String password = conf.getProperty("jdbc.password");

        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
    }

    /**
     * 获取数据库链接
     */
    public static Connection getConnection(){
        Connection conn = CONNECTION_HOLDED.get();
        if (conn == null){
            try {
                conn = DATA_SOURCE.getConnection();
            } catch (SQLException e){
                LOGGER.error("get connection failure" , e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDED.set(conn);
            }
        }
        return  conn;
    }

    /**
     * 关闭数据库链接
     */
    public static void closeConnection(){
        Connection conn = CONNECTION_HOLDED.get();
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e){
                LOGGER.error("close connection failure" , e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDED.remove();
            }
        }
    }

    /**
     * 查询实体类列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass , String sql , Object... params){
        List<T> entityList;
        try {
            Connection conn = getConnection();
            entityList = QUERY_RUNNER.query(conn , sql ,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e){
            LOGGER.error("query entity list failure" , e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entityList;
    }

    /**
     * 查询实体类
     */
    public static <T> T queryEntity(Class<T> entityClass , String sql , Object... params){
        T entity;
        try {
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn , sql ,new BeanHandler<T>(entityClass),params);
        } catch (SQLException e){
            LOGGER.error("query entity failure" , e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return entity;
    }

    /**
     * 执行查询语句
     */
    public static List<Map<String,Object>> executeQuery(String sql , Object... params){
        List<Map<String,Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn , sql ,new MapListHandler(),params);
        } catch (SQLException e){
            LOGGER.error("query entity list failure" , e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return result;
    }

    /**
     * 执行更新语句
     */
    public static int executeUpdate(String sql , Object... params){
        int rows = 0;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e){
            LOGGER.error("query entity list failure" , e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return rows;
    }

    /**
     * 插入实体
     */
    public static <T> boolean insertEntity(Class<T> entityClass , Map<String,Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not insert entity : fieldMap is empty");
            return  false;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder value = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append(", ");
            value.append("?, ");
        }

        columns.replace(columns.lastIndexOf(",") , columns.length() , ")");
        value.replace(value.lastIndexOf(",") , value.length() , ")");
        sql += columns + "VALUE" + value;
        Object[] params = fieldMap.values().toArray();
        return executeUpdate(sql, params) == 1;
    }

    /**
     * 更新实体
     */
    public static <T> boolean updateEntity(Class<T> entityClass ,long id , Map<String,Object> fieldMap) {
        if (CollectionUtil.isEmpty(fieldMap)) {
            LOGGER.error("can not update entity : fieldMap is empty");
            return  false;
        }
        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for (String fieldName : fieldMap.keySet()){
            columns.append(fieldName).append("=?, ");
        }

        sql += columns.substring(0 , columns.lastIndexOf(", ")) + "WHERE id = ?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object[] params = paramList.toArray();
        return executeUpdate(sql,params) == 1;
    }

    /**
     * 删除实体
     */
    public static <T> boolean deleteEntity(Class<T> entityClass , long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id = ?";
        return executeUpdate(sql , id) == 1;
    }


    /**
     * 获取实体类的表名
     */
    public static String getTableName(Class entityClass){
        return entityClass.getSimpleName();
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.error("begin transaction failure" , e);
                throw  new RuntimeException(e);
            } finally {
                CONNECTION_HOLDED.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("commit transaction failure" , e);
                throw new RuntimeException(e);
            } finally {
                CONNECTION_HOLDED.remove();
            }
        }
    }

    /**
     * 回归事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("rollback transaction failure" , e);
            } finally {
                CONNECTION_HOLDED.remove();
            }
        }
    }

}
