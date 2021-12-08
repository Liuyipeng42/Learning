package com.lyp.tacocloudspring.dao;

import com.lyp.tacocloudspring.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao {
    User findByUsername(String username);

    int saveUser(User user);
}
