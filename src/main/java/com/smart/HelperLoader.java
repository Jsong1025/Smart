package com.smart;

import com.smart.helper.BeanHelper;
import com.smart.helper.ClassHelper;
import com.smart.helper.ControllerHelper;
import com.smart.helper.IocHelper;
import com.smart.util.ClassUtil;

/**
 * 加载相应的Helper类
 */
public final class HelperLoader {

    public static void init(){
        Class<?>[] classList = {
                ClassHelper.class ,
                BeanHelper.class ,
                IocHelper.class ,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
