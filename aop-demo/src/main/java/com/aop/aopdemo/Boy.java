package com.aop.aopdemo;

import org.springframework.stereotype.Component;

@Component
public class Boy implements IBoy {
    public String buy() {
        System.out.println("男孩买了一个游戏机");
        return "游戏机";
    }
}
