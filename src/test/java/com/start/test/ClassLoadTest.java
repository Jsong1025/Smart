package com.start.test;

import com.smart.ConfigConstant;
import com.smart.helper.ClassHelper;
import com.smart.helper.ConfigHelper;
import com.smart.util.ClassUtil;
import org.junit.Test;

import java.util.Set;

/**
 * 测试类的加载
 */
public class ClassLoadTest {


//    @Test
    public void testLoadClass(){
        Class<?> cls = ClassUtil.loadClass("com.demo.Hello");
        System.out.println(cls);
    }

//    @Test
    public void testGetClassSet() {
        Set<Class<?>> set = ClassUtil.getClassSet("com.demo");
        System.out.println(set);
    }

//    @Test
    public void testConfigLoad() {
        System.out.println("app asset path : "+ ConfigHelper.getAppAssetPath());
        System.out.println("app base path : "+ConfigHelper.getAppBasePackage());
        System.out.println("app jsp path : "+ConfigHelper.getAppJspPath());
        System.out.println("jdbc driver : "+ConfigHelper.getJdbcDriver());
        System.out.println("jdbc url : "+ConfigHelper.getJdbcUrl());
        System.out.println("jdbc username : "+ConfigHelper.getJdbcUsername());
        System.out.println("jdbc password : "+ConfigHelper.getJdbcPassword());
    }

//    @Test
    public void testGetControllerClass(){
        Set<Class<?>> set = ClassHelper.getControllerClassSet();
        System.out.println(set);
    }

//    @Test
    public void testGetServiceClass(){
        Set<Class<?>> set = ClassHelper.getServiceClassSet();
        System.out.println(set);
    }

    @Test
    public void testGetBean(){
        Set<Class<?>> set = ClassHelper.getBeanClassSet();
        System.out.println(set);
    }


}
