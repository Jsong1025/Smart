package com.demo.aspect;

import com.smart.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015/11/21.
 */
public class ControllerAspect extends AspectProxy {

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println("-------------before--------");
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println("-------------end-------------");
    }
}
