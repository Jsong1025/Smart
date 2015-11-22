package com.demo.aspect;

import com.demo.annotation.MyAspect;
import com.smart.annotation.Aspect;
import com.smart.proxy.AspectProxy;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2015/11/21.
 */
@Aspect(MyAspect.class)
public class TestAspect extends AspectProxy {

    @Override
    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println("-------------before--------");
    }

    @Override
    public void after(Class<?> cls, Method method, Object[] params) throws Throwable {
        System.out.println("-------------end-------------");
    }
}
