package com.mybatisplus.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@Accessors(chain = true)
//设置chain=true，生成setter方法返回this(也就是返回的是对象)，代替了默认的返回void。
@TableName("user") // 指定实体对应的表名
public class User {

    // IdType.AUTO：设置主键自增，同时数据库中也要设置
    // IdType.ASSIGN_ID：雪花算法生成 id
    // 若要使用自定义的 id策略CustomIdGenerator， 则要使用 @TableId
    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;
    private Integer age;
    private String email;

    @TableField(exist = false)
    private String ignoreColumn = "ignoreColumn";

    @TableField(exist = false)
    private Integer count;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
