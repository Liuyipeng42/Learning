package com.lyp.tacocloudspring.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;


// 领域类
// 应用的领域指的是它所要解决的主题范围：也就是会影响到对应用理解的理念和概念
@Data // 会告诉 Lombok 生成所有缺失的方法，
// 如常见的 getter 和 setter方法，以及 equals()、hashCode()、toString()等方法
// 还可以给 final 赋初始值
@RequiredArgsConstructor // 生成所有以 final 属性作为参数的构造器
public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

    public Ingredient() {
        this.id = null;
        this.name = null;
        this.type = null;
    }

    public enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    @Override
    public String toString() {
        return "{'id':'" + id + "'" + ", 'name':'" + name + "'" + ", 'type':'" + type + "'}";
    }

}
