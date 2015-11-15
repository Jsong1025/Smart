package com.smart.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 流操作工具类
 *
 * @author jsong
 */
public final class StreamUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中读取字符串
     */
    public static String getString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            LOGGER.error("get string failure" , e);
            throw  new RuntimeException(e);
        }
        return sb.toString();
    }

}
