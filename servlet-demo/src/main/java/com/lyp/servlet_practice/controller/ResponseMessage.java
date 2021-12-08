package com.lyp.servlet_practice.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseMessage extends HttpServlet {

    public ResponseMessage(){
        System.out.println("在第一次请求这个Servlet接口的实现类的时候才会创建此Servlet实例");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get请求");

        // 设置content-type，告诉浏览器要使用 text和 html的编译器，并且使用的字符集是utf-8，否则不能显示中文
        // 默认使用的是 text编译器，如果不设置，会将 html代码作为字符串显示
        // 设置字符集时，必须要在 获取Tomcat中的输出流 之前设置，否则无效
        resp.setContentType("text/html;charset=UTF-8");

        // 获取Tomcat中的输出流
        PrintWriter out = resp.getWriter();

        String string = "The answer to life, universe and everything is 42";
        out.write(string); // 将执行结果以二进制的形式写入到响应体

        String html = "<br/><br/>第一行<br/>第二行<br/>第三行<br/><br/>"; // 响应的内容中既有 Text 也有 Html
        out.write(html);

        int integer = 42;
        out.write(integer); // 浏览器显示的结果不是 42，是 *，因为 * 的 ASCII码是 42
        out.print(integer); // print 方法可以让浏览器显示数字
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("post请求");
    }
}
