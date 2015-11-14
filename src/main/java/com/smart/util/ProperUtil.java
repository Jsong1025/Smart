package com.smart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 属性文件工具类
 */
public final class ProperUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProperUtil.class);

    /**
     * 加载配置文件
     */
    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (in == null) {
                throw new FileNotFoundException(fileName + "file is no find");
            }
            props = new Properties();
            props.load(in);
        } catch (IOException e) {
            LOGGER.error("load properties file failure", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }
        return props;
    }

    /**
     * 获取字符型属性
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }
    public static String getString(Properties props, String key, String defaultKey) {
        String value = defaultKey;
        if (props.containsKey(key)){
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 获取数值型属性
     */
    public static int getInt(Properties props , String key){
        return getInt(props,key,0);
    }
    public static int getInt(Properties props , String key , int defaultKey){
        int value = defaultKey;
        if (props.containsKey(key)){
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * 获取布尔类型属性
     */
    public static boolean getBoolean(Properties props , String key){
        return  getBoolean(props,key,false);
    }
    public static boolean getBoolean(Properties props , String key , boolean defauleKey) {
        boolean value = defauleKey;
        if (props.containsKey(key)){
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}