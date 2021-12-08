package com.aop.aopdemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AopDemoApplicationTests {

    Boy boy;
    Girl girl;

    @Autowired
    public void setBoyGirl(Boy boy, Girl girl) {
        this.boy = boy;
        this.girl = girl;
    }

    @Test
    void AOPTest() {
        boy.buy();
        System.out.println();
        System.out.println("女孩买了: " + girl.buy(44));
    }

}
