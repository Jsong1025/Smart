package com.smart.helper;

import com.smart.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean  助手类
 *
 * @author jsong
 */
public final class BeanHelper {

    /**
     * 定义Bean映射（用于存放Bean类与Bean实例的映射关系）
     */
    private static final Map<Class<?> , Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstace(beanClass);
            BEAN_MAP.put(beanClass , obj);
        }
    }

    /**
     * 获取bean映射
     */
    public static Map<Class<?> , Object> getBeanMap() {
        return BEAN_MAP;
    }

    /**
     * 获取Bean实例
     */
    public static <T> T getBean(Class<T> cls) {
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("can not get bean by class :" + cls);
        }
        return (T)BEAN_MAP.get(cls);
    }
}
