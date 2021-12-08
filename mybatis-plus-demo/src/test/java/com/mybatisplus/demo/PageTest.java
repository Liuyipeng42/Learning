package com.mybatisplus.demo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.entity.User;
import com.mybatisplus.demo.model.UserChildren;
import com.mybatisplus.demo.model.MyPage;
import com.mybatisplus.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class PageTest {

    @Autowired
    private UserService userService;

    @Test
    public void iPageSelectTest() {

        // 1. 创建配置文件 MybatisPlusConfig
        // 2. 参数传入 Page类
        // 3. 若有判断的条件，则将 sql写在 mapper 的 @select注解中，参数可以查询参数可以通过传参的方式，或创建一个 继承 Page的类，作为此类的属性
        List<User> userList = userService.iPageSelect(new MyPage<>(2, 2));
        System.out.println();
        System.out.println(userService.page(new Page<>(2, 2)).getRecords());
        userList.forEach(System.out::println);
        System.out.println();
    }

    @Test
    public void mySelectPageMapTest() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "%J%");
        List<User> userList = userService.mySelectPageMap(new MyPage<>(0, 2), map).getRecords();
        System.out.println();
        userList.forEach(System.out::println);
        System.out.println();
    }


    // 多表分页查询
    @Test
    public void userChildrenPageTest() {
        List<UserChildren> userChildrenList = userService.userChildrenPage(new MyPage<>(0, 2)).getRecords();
        System.out.println();
        userChildrenList.forEach(System.out::println);
        System.out.println();
    }
}

