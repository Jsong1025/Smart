package com.smart.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理管理类
 *
 * @author jsong
 */
public class ProxyManager {

    /**
     * 创建指定方法的代理对象
     */
    public static <T> T createProxy (final Class<?> targetClass , final List<Proxy> proxyList) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams,
                                    MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass , targetObject ,targetMethod ,methodProxy , methodParams ,proxyList).doProxyChain();
            }
        });
        return (T) enhancer.create();
    }

}
