package com.aop.aopdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
// @EnableAspectJAutoProxy注解表明启用 AOP 功能
// 参数 proxyTargetClass，默认为 false
// proxyTargetClass 为 true，会使用 cglib 的动态代理方式
// 使用这种方式时，若拓展类的方法被 final修饰，则无法进行织入
// proxyTargetClass 为 false, 通过jdk的基于接口的方式进行织入
// 使用这种方式时，若目标对象没有接口，则会使用 cglib 的动态代理方式
@EnableAspectJAutoProxy(proxyTargetClass = true)
// 此 Demo中 Boy类有接口 IBoy，Girl类没有接口
public class AopDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopDemoApplication.class, args);
    }

}
