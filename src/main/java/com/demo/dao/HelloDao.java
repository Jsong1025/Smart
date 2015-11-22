package com.demo.dao;

import com.smart.annotation.Service;
import com.smart.annotation.Transaction;

/**
 * Created by Administrator on 2015/11/21.
 */
@Service
public class HelloDao {

    @Transaction
    public String sayHello(){
        return "hello";
    }

}
