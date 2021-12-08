package com.mybatisplus.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mybatisplus.demo.entity.User;
import com.mybatisplus.demo.model.MyPage;
import com.mybatisplus.demo.model.ParamSome;
import com.mybatisplus.demo.model.UserChildren;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {

    MyPage<User> mySelectPage(MyPage<User> myPage, ParamSome paramSome);

    MyPage<UserChildren> userChildrenPage(MyPage<UserChildren> myPage);

    MyPage<User> mySelectPageMap(MyPage<User> pg, Map<String, String> map);

    List<User> mySelectMap(Map<String, Object> param);

    List<User> myPageSelect(MyPage<User> myPage);

    List<User> iPageSelect(Page<User> myPage);

    List<User> rowBoundList(RowBounds rowBounds, Map<String, Object> map);

}
