package com.mybatis.demo;

import com.mybatis.demo.dao.StudentDao;
import com.mybatis.demo.domain.Student;

import org.junit.jupiter.api.Test;


class MybatisDemoApplicationTests {

    @Test
    void test() {
        StudentDao studentDao = new StudentDao();
        Student student = studentDao.selectStudentsById("1");
        System.out.println(student);
    }

}
