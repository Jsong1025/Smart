package com.smart;

import com.smart.helper.*;
import com.smart.util.ClassUtil;

/**
 * 加载相应的Helper类
 */
public final class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class ,
                BeanHelper.class ,
                AopHelper.class ,
                IocHelper.class ,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
