package com.smart.helper;

import com.smart.ConfigConstant;
import com.smart.util.ProperUtil;

import java.util.Properties;

/**
 *属性文件助手类
 *
 * @author jsong
 *
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = ProperUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取JDBC驱动
     */
    public static String getJdbcDriver(){
        return ProperUtil.getString(CONFIG_PROPS , ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取JDBC URL
     */
    public static String getJdbcUrl(){
        return ProperUtil.getString(CONFIG_PROPS , ConfigConstant.JDBC_URL);
    }

    /**
     * 获取JDBC用户名
     */
    public static String getJdbcUsername(){
        return ProperUtil.getString(CONFIG_PROPS , ConfigConstant.JDBC_USERNAEM);
    }

    /**
     * 获取JDBC密码
     */
    public static String getJdbcPassword(){
        return ProperUtil.getString(CONFIG_PROPS , ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础报名
     */
    public static String getAppBasePackage(){
        return ProperUtil.getString(CONFIG_PROPS , ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用JSP路径
     */
    public static String getAppJspPath(){
        return ProperUtil.getString(CONFIG_PROPS , ConfigConstant.APP_JSP_PATH , "/WEB-INF/view/");
    }

    /**
     * 获取静态资源路径
     */
    public static String getAppAssetPath(){
        return ProperUtil.getString(CONFIG_PROPS , ConfigConstant.APP_ASSET_PATH , "/asset/");
    }

}
