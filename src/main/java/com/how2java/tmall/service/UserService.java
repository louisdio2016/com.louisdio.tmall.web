package com.how2java.tmall.service;

import com.how2java.tmall.pojo.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void delete(int id);
    List<User> list();
    void update(User user);
    User get(int id);
}
