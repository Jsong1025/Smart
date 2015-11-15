package com.start.test;

import com.smart.helper.BeanHelper;
import com.smart.helper.IocHelper;
import org.junit.Test;

/**
 * 测试依赖注入
 */
public class IocTest {

    @Test
    public void testBeanMap() {
        System.out.println(BeanHelper.getBeanMap());
    }

    public void testIoc() throws Exception{
        Class.forName(IocHelper.class.getName());
    }

}
