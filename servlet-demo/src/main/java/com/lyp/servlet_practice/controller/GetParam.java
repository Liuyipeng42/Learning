package com.lyp.servlet_practice.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class GetParam extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 获取请求url中的 参数名
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()){
            String paramName = paramNames.nextElement();
            // 2. 获取参数名对应的值
            System.out.println(paramName + " = " +req.getParameter(paramName));
        }
    }

    // 浏览器以GET方式发送请求，请求参数保存在【请求头】，在Http请求协议包到达Http服务器之后，第一件事就是进行解码
    // 请求头二进制内容由Tomcat负责解码，Tomcat9,6默认使用【utf-8】字符集，可以解释一切国家文字

    // 浏览器以POST方式发送请求，请求参数保存在【请求体】，在Http请求协议包到达Http服务器之后，第一件事就是进行解码
    // 请求体二进制内容由当前请求对象(request)负责解码。request默认使用［ IS0-8859-1］字符集，一个东欧语系字符集
    // 此时如果请求体参数内容是中文，将无法解码只能得到乱码
    // 可以通过设置字符集来解决  req.setCharacterEncoding("utf-8");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        // 1. 获取请求中的参数名
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()){
            String paramName = paramNames.nextElement();
            // 2. 获取参数名对应的值
            System.out.println(paramName + " = " +req.getParameter(paramName));
        }
    }
}
