package com.lyp.servlet_practice.controller;

import com.lyp.servlet_practice.HelloServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ReadRequest extends HelloServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 1. 获取请求的 Url
        String url = request.getRequestURL().toString();

        // 2. 获取请求的 Uri
        String uri = request.getRequestURI();

        // 3. 获取请求方式
        String method = request.getMethod();

        System.out.println(url);
        System.out.println(uri);
        System.out.println(method);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
