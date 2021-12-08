package com.mybatisplus.demo.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatisplus.demo.entity.User;
import com.mybatisplus.demo.model.ParamSome;
import com.mybatisplus.demo.model.UserChildren;
import com.mybatisplus.demo.mapper.ChildrenMapper;
import com.mybatisplus.demo.mapper.UserMapper;
import com.mybatisplus.demo.model.MyPage;
import com.mybatisplus.demo.service.UserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    UserMapper userMapper;
    ChildrenMapper childrenMapper;

    @Resource
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Resource
    public void setChildrenMapper(ChildrenMapper childrenMapper) {
        this.childrenMapper = childrenMapper;
    }

    @Override
    public MyPage<User> mySelectPage(MyPage<User> myPage, ParamSome paramSome){
        return userMapper.mySelectPage(myPage, paramSome);
    }

    @Override
    public MyPage<UserChildren> userChildrenPage(MyPage<UserChildren> myPage){
        return userMapper.userChildrenPage(myPage);
    }

    @Override
    public MyPage<User> mySelectPageMap(MyPage<User> pg, Map<String, String> map){
        return userMapper.mySelectPageMap(pg, map);
    }

    @Override
    public List<User> mySelectMap(Map<String, Object> param){
        return userMapper.mySelectMap(param);
    }

    @Override
    public List<User> myPageSelect(MyPage<User> myPage){
        return userMapper.myPageSelect(myPage);
    }

    @Override
    public List<User> iPageSelect(Page<User> myPage){
        return userMapper.iPageSelect(myPage);
    }

    @Override
    public List<User> rowBoundList(RowBounds rowBounds, Map<String, Object> map){
        return userMapper.rowBoundList(rowBounds, map);
    }

}
