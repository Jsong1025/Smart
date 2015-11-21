package com.smart.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 *
 * @author jsong
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {

    /**
     * 注解
     */
    Class<? extends Annotation> value();

}
