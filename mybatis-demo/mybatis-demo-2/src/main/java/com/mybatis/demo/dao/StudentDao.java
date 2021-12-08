package com.mybatis.demo.dao;

import com.mybatis.demo.domain.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentDao {

    /**
     * 一个简单类型的参数：
     *   简单类型： mybatis把java的基本数据类型和String都叫简单类型。
     *  在mapper文件获取简单类型的一个参数的值，使用 #{任意字符}
     */
    Map<Object, Object> selectStudentsById(int id);

    // 使用resultMap
    List<Student> selectAllStudents();

    /**
     * 多个参数，使用java对象作为接口中方法的参数
     */
    List<Student> selectStudentsByNameAge(@Param("SelectName") String name,
                                          @Param("SelectAge") int age);

    /**
     * 多个参数，使用java对象作为接口中方法的参数
     */
    List<Student> selectStudentsByNameId(Student student);

    /**
     * 多个参数，使用Map存放多个值
     */
    List<Student> selectStudentsByAgeId(Map<String, Object> map);

    // 模糊查询
    List<Student> selectStudentsByNameLike(String pattern);

    int countStudents();

    int insertStudent(Student student);

}
