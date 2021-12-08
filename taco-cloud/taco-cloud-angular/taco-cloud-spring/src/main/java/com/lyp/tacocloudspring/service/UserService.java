package com.lyp.tacocloudspring.service;


import com.lyp.tacocloudspring.entity.User;

public interface UserService {
    User findByUsername(String username);

    int saveUser(User user);
}
