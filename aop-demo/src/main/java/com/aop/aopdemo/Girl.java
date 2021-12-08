package com.aop.aopdemo;


import org.springframework.stereotype.Component;

@Component
public class Girl {
    public String buy(double price) {
        System.out.printf("女孩花了%s元买了一件漂亮的衣服%n", price);
        return "衣服";
    }
}
