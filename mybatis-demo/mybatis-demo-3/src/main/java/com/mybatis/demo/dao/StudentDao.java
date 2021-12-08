package com.mybatis.demo.dao;

import com.mybatis.demo.domain.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentDao {
    // 动态 sql，使用 java对象作为参数
    // if标签
    List<Student> selectStudentsByName(Student student);

    // where标签
    List<Student> selectStudentsByNameAge(Student student);

    // foreach标签
    // List<Integer>类型
    List<Student> selectStudentsByIds(List<Integer> idList);

    // List<Object>类型
    List<Student> selectStudentsByStudentId(List<Student> studentList);

    List<Student> selectAllStudents();
}
