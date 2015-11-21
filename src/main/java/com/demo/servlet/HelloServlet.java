package com.demo.servlet;

import com.demo.dao.HelloDao;
import com.smart.annotation.Action;
import com.smart.annotation.Controller;
import com.smart.annotation.Inject;
import com.smart.bean.Param;
import com.smart.bean.View;

@Controller
public class HelloServlet {

    @Inject
    private HelloDao dao;

    @Action("get:/hello")
    public View sayHello(Param param){
        String str = dao.sayHello();
        System.out.println("hello");
        return new View("hello.jsp").addModel("hello",str);
    }
}
