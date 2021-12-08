package com.lyp.servlet_practice.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UseLoadOnStartup extends HttpServlet {

    public UseLoadOnStartup(){
        System.out.println("在web.xml中设置了 load-on-startup，在服务启动时会创建此Servlet实例");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get请求");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post请求");
    }
}
