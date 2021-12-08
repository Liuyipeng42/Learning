package com.mybatis.demo.utills;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils {
    private static SqlSessionFactory factory = null;

    // 静态块在JVM中只执行一次
    static {
        // 访问mybatis读取student数据
        // 1.定义mybatis主配置文件名称  路径从 classes/... 开始
        String config = "mybatis.xml";
        // 2.读取config表示的文件
        InputStream in = null;
        try {
            in = Resources.getResourceAsStream(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 3.创建 SqlSessionFactoryBuilder对象
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        // 4.创建 SqlSessionFactory对象
        factory = builder.build(in);
    }

    public static SqlSession getSqlSession(){
        // 5.获取 sqlSession对象,从 SqlSessionFactory中获取 qlSession
        //   若传入 true参数表示自动提交事务 factory.openSession(true)
        //   SqlSession是一个接口，定义了数据操作的方法
        return factory.openSession();
    }

}
