package com.mybatisplus.demo.controller;

import com.mybatisplus.demo.entity.User;
import com.mybatisplus.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public List<User> test(HttpServletRequest request){
        HttpSession session = request.getSession();
        //第二步：将想要保存到数据存入session中
        session.setAttribute("name", "hhh");

        return userService.list();
    }

    @GetMapping("/get")
    public String get(HttpServletRequest request){
        return (String) request.getSession().getAttribute("name");
    }

}
