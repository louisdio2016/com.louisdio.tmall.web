package com.how2java.tmall.mapper;


import com.how2java.tmall.pojo.Menu;

import java.util.List;

public interface MenuMapper {
    Menu selectByPrimaryKey(Integer id);

    List<Menu> selectMenuList();

    void insertMenu(Menu menu);

    void deleteMenu(Integer id);

    void deleteMenuList(List<Integer> midList);

    void updateMenu(Menu menu);
}
