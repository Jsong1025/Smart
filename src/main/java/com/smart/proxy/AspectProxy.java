package com.smart.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 切面代理类
 *
 * @author jsong
 */
public abstract class AspectProxy implements Proxy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain chain) throws Throwable {
        Object result = null;
        Class<?> cls = chain.getTargetClass();
        Method method = chain.getTargetMethod();
        Object[] params = chain.getMethodParams();

        begin();
        try {
            if (intercept(cls , method , params)) {
                before(cls , method , params);
                result = chain.doProxyChain();
                after(cls , method , params);
            } else {
                result = chain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure" , e);
            error(cls , method , params ,e);
            throw e;
        } finally {
            end();
        }

        return result;
    }

    public void begin(){
    }

    public boolean intercept (Class<?> cls , Method method , Object[] params) throws Throwable{
        return true;
    }

    public void before (Class<?> cls , Method method , Object[] params) throws Throwable{
    }

    public void after (Class<?> cls , Method method , Object[] params) throws Throwable{
    }

    public void error (Class<?> cls , Method method , Object[] params ,Throwable e ) {
    }

    public void end () {
    }

}
