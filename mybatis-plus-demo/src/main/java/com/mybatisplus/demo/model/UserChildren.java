package com.mybatisplus.demo.model;

import com.mybatisplus.demo.entity.Children;
import com.mybatisplus.demo.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserChildren extends User {

    private List<Children> c;
}
