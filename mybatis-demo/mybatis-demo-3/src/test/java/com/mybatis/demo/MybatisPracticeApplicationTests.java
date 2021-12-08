package com.mybatis.demo;

import com.github.pagehelper.PageHelper;
import com.mybatis.demo.dao.StudentDao;
import com.mybatis.demo.domain.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class MybatisPracticeApplicationTests {

    @Autowired
    StudentDao studentDao;

    @Test
    void test() {

        List<Student> studentList;

        // if标签
        System.out.println("if标签");
        Student student = new Student();
        student.setName("hhh");
        studentList = studentDao.selectStudentsByName(student);
        studentList.forEach(System.out::println);
        System.out.println();

        // where标签
        System.out.println("where标签");
        student.setAge(30);
        studentList = studentDao.selectStudentsByNameAge(student);
        studentList.forEach(System.out::println);
        System.out.println();

        // foreach标签
        // List<Integer>类型
        System.out.println("foreach标签 List<Integer>类型");
        List<Integer> idList = new ArrayList<>();
        idList.add(1046);
        idList.add(1008);
        idList.add(123);
        studentList = studentDao.selectStudentsByIds(idList);
        studentList.forEach(System.out::println);
        System.out.println();

        // List<Object>类型
        System.out.println("foreach标签 List<Object>类型");
        List<Student> students = new ArrayList<>();
        students.add(new Student(1001,"hhh"));
        students.add(new Student(1002,"hhh"));
        students.add(new Student(1008,"hhh"));
        studentList = studentDao.selectStudentsByStudentId(students);
        studentList.forEach(System.out::println);
        System.out.println();

        // 分页查询
        System.out.println("分页查询");
        PageHelper.startPage(1, 3);
        studentList = studentDao.selectAllStudents();
        studentList.forEach(System.out::println);
    }

}
