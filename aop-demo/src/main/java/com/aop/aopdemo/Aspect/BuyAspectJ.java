package com.aop.aopdemo.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


// Spring借助 AspectJ的切点表达式语言来定义Spring切面
// execution()：用于 匹配 是连接点的执行方法
// args()：限制连接点匹配 参数为指定类型 的执行方法
// @args()：限制连接点匹配 参数由指定注解标注 的执行方法
// this()：限制连接点匹配 AOP代理的 Bean引用为指定类型 的类
// @target()：限制连接点匹配 特定的执行对象，这些对象对应的类要具备指定类型的注解
// within()：限制连接点匹配 指定的类型
// @within()：限制连接点匹配 指定注解所标注的类型（当使 用Spring AOP时，方法定义在由指定的注解所标注的类里）
// @annotation：限制匹配带有 指定注解连接点

// Spring AOP 中有 5 中通知类型
// @Before：通知方法会在目标方法 调用之前执行
// @After：通知方法会在目标方法 返回或异常后调用
// @AfterReturning：通知方法会在目标方法 返回后调用
// @AfterThrowing：通知方法会在目标方法 抛出异常后调用
// @Around：通知方法会在目标方法 封装起来


// 只有 execution指示器是唯一的 执行匹配，而其他的指示器都是用于 限制匹配
@Aspect
@Component
public class BuyAspectJ {

    // 可以使用 @Pointcut注解声明切点表达式，然后使用表达式
    // 方法表达式以 * 号开始，标识不关心方法的返回值类型
    // 方法参数列表使用 .. 标识切点选择任意的参数的此方法
    @Pointcut("execution(* com.aop.aopdemo.IBoy.buy(..))")
    public void APointcut(){}

    // 多个匹配之间可以使用链接符 &&、||、！来表示 “且”、“或”、“非”的关系
    @After("within(com.example.aopdemo.*) && !bean(girl)")
    public void nameIsDoesntMatter(){
        System.out.println("男孩买到了自己喜欢的东西");
    }

    // 使用 Pointcut时只需写上 Pointcut注解修饰的方法名加上 “()”即可
    @Before("APointcut()")
    public void hhh() {
        System.out.println("before ...");
    }

    @After("APointcut()")
    public void xxx() {
        System.out.println("After ...");
    }

    @AfterReturning("APointcut()")
    public void yyy() {
        System.out.println("AfterReturning ...");
    }

    // @Around 修饰的环绕通知类型，是将整个目标方法封装起来了，
    // 在使用时，传入了 ProceedingJoinPoint 类型的参数，这个对象是必须要有的，
    // 并且需要调用 ProceedingJoinPoint 的 proceed() 方法，用于调用指定的方法。
    // 否则 表示原目标方法将会被阻塞调用
    @Around("APointcut()")
    public Object zzz(ProceedingJoinPoint pj) {
        try {
            System.out.println("Around aaa ...");
            pj.proceed();
            System.out.println("Around bbb ...");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    // args(price) 用于表明传入的参数名
    @Around("execution(String com.aop.aopdemo.Girl.buy(double)) && args(price) && bean(girl)")
    public String returnValue(ProceedingJoinPoint pj, double price){
        try {
            pj.proceed();
            if (price > 42) {
                System.out.println("女孩买衣服超过了42元，赠送一双袜子");
                return "衣服和袜子";
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return "衣服";
    }
}
