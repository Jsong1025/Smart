package com.smart.helper;

import com.smart.annotation.Aspect;
import com.smart.annotation.Service;
import com.smart.proxy.AspectProxy;
import com.smart.proxy.Proxy;
import com.smart.proxy.ProxyManager;
import com.smart.proxy.TransationProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 方法拦截助手类
 *
 * @author jsong
 */
public final class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?> , Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?> , List<Proxy>> targetMap = createTargetMap(proxyMap);

            for (Map.Entry<Class<?> , List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();

                // 创建代理
                Object proxy = ProxyManager.createProxy(targetClass , proxyList);
                // 放入对象集合中
                BeanHelper.setBean(targetClass , proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop failure" , e);
        }
    }

    /**
     * 创建切面类型与被代理类型的映射，Map中节点<切面类型 ，含有被代理注解的类型集合>
     */
    private static Map<Class<?> , Set<Class<?>>> createProxyMap() throws Exception {
        Map<Class<?> , Set<Class<?>>> proxyMap = new HashMap<>();

        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    /**
     * 获取aspect.value()注解注释的目标类集合
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        Set<Class<?>> targetClassSet = new HashSet<>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {

            // 获取aspect.value()注解的所有类，并全部放入targetClassSet中
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 创建被代理类型与切面对象的映射 , Map节点<被代理类型 ，切面对象集合 >
     */
    private static Map<Class<?> , List<Proxy>> createTargetMap(Map<Class<?> , Set<Class<?>>> proxyMap)
            throws Exception {
        Map<Class<?> , List<Proxy>> targetMap = new HashMap<>();

        for (Map.Entry<Class<?> , Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            // 切面类型
            Class<?> proxyClass = proxyEntry.getKey();
            // 被代理类型
            Set<Class<?>> targetClassSet = proxyEntry.getValue();

            for (Class<?> targetClass : targetClassSet) {
                // 创建切面对象
                Proxy proxy = (Proxy)proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    // 如果映射中已有当前被代理类型,直接添加
                    targetMap.get(targetClass).add(proxy);
                } else {
                    // 如没有，创建新的集合并加入映射
                    List<Proxy> proxyList = new ArrayList<>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass , proxyList);
                }
            }
        }
        return targetMap;
    }

    private static void addAspectProxy(Map<Class<?> , Set<Class<?>>> proxyMap) throws Exception {
        // 获取Aspect中value注解所标注的所有类型
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {

            // 检查类上是否有@Aspect注解
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);

                // 获取Aspect中value注解所标注的所有类型
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass , targetClassSet);
            }
        }
    }

    private static void addTransactionProxy(Map<Class<?> , Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransationProxy.class , serviceClassSet);
    }

}
