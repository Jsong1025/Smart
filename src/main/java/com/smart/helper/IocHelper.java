package com.smart.helper;

import com.smart.annotation.Inject;
import com.smart.util.ArrayUtil;
import com.smart.util.CollectionUtil;
import com.smart.util.ReflectionUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 *
 * @author jsong
 */
public final class IocHelper {

     static {

        // 获取所有的Bean类与Bean实例之间的映射关系（Bean Map）
        Map<Class<?> , Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {

            // 遍历Bean Map
            for (Map.Entry<Class<?> , Object> beanEntity : beanMap.entrySet()) {

                // 从BeanMap中获取Bean类与Bean实例
                Class<?> beanClass = beanEntity.getKey();
                Object beanInstance = beanEntity.getValue();

                // 获取Bean类定义的所有成员变量（Bean Field）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {

                    // 遍历Bean Field
                    for (Field beanField : beanFields){

                        // 判断当前Bean Field是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {

                            // 在Bean Map中获取Bean Field对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            if (beanFieldInstance != null) {

                                // 通过反射初始化Bean Field的值
                                ReflectionUtil.setField(beanInstance , beanField ,beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }

}
