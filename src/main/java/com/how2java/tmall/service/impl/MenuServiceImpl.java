package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.MenuMapper;
import com.how2java.tmall.pojo.Menu;
import com.how2java.tmall.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    @Transactional(readOnly = true)
    public List<Menu> list() {
        return menuMapper.selectMenuList();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void add(Menu menu) {
        menuMapper.insertMenu(menu);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void delete(Integer id) {
        menuMapper.deleteMenu(id);
    }

    @Override
    public Menu get(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void update(Menu menu) {
        menuMapper.updateMenu(menu);
    }

    @Override
    public void fillRoles(List<Menu> menus) {
        for(Menu menu:menus){
            fillRoles(menu);
        }
    }

    @Override
    public void fillRoles(Menu menu) {
        Menu menu1 = menuMapper.selectByPrimaryKey(menu.getId());
        menu.setRoles(menu1.getRoles());
    }
}
