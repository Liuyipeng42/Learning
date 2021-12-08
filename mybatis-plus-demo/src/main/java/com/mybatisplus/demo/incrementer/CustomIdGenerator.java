package com.mybatisplus.demo.incrementer;


import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;

import lombok.extern.slf4j.Slf4j;


// 自定义 id生成
@Slf4j
@Component
public class CustomIdGenerator implements IdentifierGenerator {

    private Long id = 6L;

    @Override
    public Number nextId(Object entity) {
        id += 1;
        return id;
    }
}
