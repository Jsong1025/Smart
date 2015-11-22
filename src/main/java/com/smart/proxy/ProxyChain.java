package com.smart.proxy;

import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理链
 *
 * @author jsong
 */
public class ProxyChain {

    private final Class<?> targetClass;     // 目标类
    private final Object targetObject;      // 目标对象
    private final Method targetMethod;      // 目标方法
    private final MethodProxy methodProxy;  // 目标方法代理
    private final Object[] methodParams;    // 目标方法参数

    private List<Proxy> proxyList = new ArrayList<>();
    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod,
                      MethodProxy methodProxy , Object[] methodParams,  List<Proxy> proxyList) {
        this.proxyList = proxyList;
        this.methodParams = methodParams;
        this.methodProxy = methodProxy;
        this.targetMethod = targetMethod;
        this.targetObject = targetObject;
        this.targetClass = targetClass;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Object doProxyChain() throws Throwable {
        Object result;
        if (proxyIndex < proxyList.size()){
            result = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            result = methodProxy.invokeSuper(targetObject , methodParams);
        }
        return result;
    }
}
