package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> list();
    //List<Menu> list(Page page);
    //int total();
    void add(Menu menu);
    void delete(Integer id);
    Menu get(Integer id);
    void update(Menu menu);
    void fillRoles(List<Menu> menus);
    void fillRoles(Menu menu);
}
