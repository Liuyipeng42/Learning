package com.mybatis.demo;

import com.mybatis.demo.dao.StudentDao;
import com.mybatis.demo.domain.Student;
import com.mybatis.demo.utills.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MybatisDemoApplicationTests {

    @Test
    void test() {

        // mybatis动态代理：
        //   mybatis根据duo的方法调用，获取执行sql语句的信息。
        //   mybatis根据你的 dao接口，创建出一个已有接口的实现类，并创建这个类的对象。
        //   完成 SqLSession调用方法，访问数据库。

        //   1、dao对象：类型是 StudentDao，全限定名称是: com.mybatispractice.dao.StudentDao，全限定名称和 namespace是一样的。
        //      方法名称：selectStudents，这个方法名和 mapper文件中的 id值 selectStudents一样
        //      所以 mybatis就可以根据对象的 全限定名称和方法名 来确定要使用 mapper中的哪个 sql语句

        //   2、通过 dao中方法的返回值也可以确定 MyBatis要调用的 SqlSession的方法
        //      如果返回值是 List，调用的是 SqlSession.selectList方法。
        //      如果返回值 int，或是非 List的，
        //      mapper文件中的<insert>， <update>就会调用 SqLSession的 insert，update等方法

        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        List<Student> studentList;

        // 使用 mybatis的动态代理机制，使用 SqlSession.getMapper()
        // getMapper能获取dao接口对应的实现类对象
        StudentDao dao = sqlSession.getMapper(StudentDao.class);

        // 返回值是 map
        Map<Object, Object> studentMap = dao.selectStudentsById(123);
        System.out.println(studentMap);
        System.out.println();

        // 返回值是 resultMap
        studentList = dao.selectAllStudents();
        studentList.forEach(System.out::println);
        System.out.println();

        // 多参数查询
        studentList = dao.selectStudentsByNameAge("hhh", 42);
        studentList.forEach(System.out::println);
        System.out.println();

        studentList = dao.selectStudentsByNameId(new Student(123, "hhh"));
        studentList.forEach(System.out::println);
        System.out.println();

        Map<String, Object> param = new HashMap<>();
        param.put("SelectAge", 42);
        param.put("SelectId", 123);
        studentList = dao.selectStudentsByAgeId(param);
        studentList.forEach(System.out::println);
        System.out.println();

        // 返回值是简单类型
        int countStudent = dao.countStudents();
        System.out.println(countStudent);
        System.out.println();

        // 插入数据
        Student student = new Student("hhh", "4323@qq.com", 432);
        int num = dao.insertStudent(student);
        sqlSession.commit();
        System.out.println(num);

        // 模糊查询
        studentList = dao.selectStudentsByNameLike("%h%");
        studentList.forEach(System.out::println);
        System.out.println();

        sqlSession.close();
    }

}
