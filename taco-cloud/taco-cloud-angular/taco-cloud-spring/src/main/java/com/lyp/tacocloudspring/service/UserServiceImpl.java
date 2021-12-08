package com.lyp.tacocloudspring.service;

import com.lyp.tacocloudspring.dao.UserDao;
import com.lyp.tacocloudspring.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public int saveUser(User user) {
        return userDao.saveUser(user);
    }
}
