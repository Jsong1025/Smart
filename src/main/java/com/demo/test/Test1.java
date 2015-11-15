package com.demo.test;

import com.demo.Hello;
import com.smart.annotation.Inject;
import com.smart.annotation.Service;

/**
 * Created by Administrator on 2015/11/15.
 */
@Service
public class Test1 {

    @Inject
    private Hello h;

    public Hello getH() {
        return h;
    }

    public void setH(Hello h) {
        this.h = h;
    }
}
