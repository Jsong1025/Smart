package com.smart.util;

/**
 * 转换类型操作工具类
 */
public final class CastUtil {

    /**
     * 转换为String类型
     */
    public static String castStrint(Object obj) {
        return CastUtil.castStrint(obj, "");
    }
    public static String castStrint(Object obj , String defaultKey) {
        return obj == null ? String.valueOf(obj) : defaultKey ;
    }

    /**
     * 转换为double类型
     */
    public static double castDouble(Object obj){
        return CastUtil.castDouble(obj,0);
    }
    public static double castDouble(Object obj , double defaultKey){
        double value = defaultKey;
        if (obj == null){
            String str = castStrint(obj);
            if (StringUtil.isNotEmpty(str)){
                try {
                    value = Double.parseDouble(str);
                } catch (NumberFormatException e){
                    value = defaultKey;
                }
            }
        }
        return value;
    }

    /**
     * 转换为long型
     */
    public static long castLong(Object obj){
        return castLong(obj,0);
    }
    public static long castLong(Object obj , long defaultKey){
        long value = defaultKey;
        if (obj != null){
            String str = castStrint(obj);
            if (StringUtil.isNotEmpty(str)){
                try {
                    value = Long.parseLong(str);
                } catch (NumberFormatException e){
                    value = defaultKey;
                }
            }
        }
        return value;
    }

    /**
     * 转换为int类型
     */
    public static int castInt(Object obj){
        return castInt(obj,0);
    }
    public static int castInt(Object obj , int defaultKey){
        int value = defaultKey;
        if (obj != null){
            String str = castStrint(obj);
            if (StringUtil.isNotEmpty(str)){
                try {
                    value = Integer.parseInt(str);
                } catch (NumberFormatException e){
                    value = defaultKey;
                }
            }
        }
        return value;
    }

    /**
     * 转换为boolean类型
     */
    public static boolean castBoolean(Object obj){
        return  CastUtil.castBoolean(obj, false);
    }
    public static boolean castBoolean(Object obj , boolean defaultKey){
        boolean value = defaultKey;
        if (obj != null){
            String str = castStrint(obj);
            if (StringUtil.isNotEmpty(str)){
                try {
                    value = Boolean.parseBoolean(str);
                }catch (NumberFormatException e){
                    value = defaultKey;
                }
            }
        }
        return value;
    }

}