package com.mybatis.demo.dao;

import com.mybatis.demo.domain.Student;
import com.mybatis.demo.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

@Component
public class StudentDao {

    public Student selectStudentsById(String id) {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        String sqlId = "com.mybatis.demo.dao.StudentDao.selectStudentsById";
        Student student = sqlSession.selectOne(sqlId, id);
        sqlSession.close();
        return student;
    }

    public int insertStudent(Student student) {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        // 6.指定要执行的 sql语句的标识, sql映射文件中的 namespace + "." + 标签的 id值
        String sqlId = "com.mybatis.demo.dao.StudentDao.insertStudents";
        int num = 0;
        try {
            // 7.执行 sql语句, 通过 sqlID找到语句
            num = sqlSession.insert(sqlId, student);
            // 8.提交事务
            sqlSession.commit();
            System.out.println(num);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            System.out.println(e.toString());
        }
        // 9.关闭SqlSession对象
        sqlSession.close();
        return num;
    }
}
