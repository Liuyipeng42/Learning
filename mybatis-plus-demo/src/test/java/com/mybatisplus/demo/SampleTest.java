package com.mybatisplus.demo;

import com.mybatisplus.demo.entity.User;
import com.mybatisplus.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SampleTest {

    @Autowired
    private UserService userService;

    @Test
    public void testSelect() {
        List<User> userList = userService.list(null);
        userList.forEach(System.out::println);
    }

}
