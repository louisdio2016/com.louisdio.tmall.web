package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(User user) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<User> list() {
        UserExample example = new UserExample();
        example.setOrderByClause("id DESC");
        List<User> userList = userMapper.selectByExample(example);
        return userList;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public User get(int id) {
        return null;
    }
}
