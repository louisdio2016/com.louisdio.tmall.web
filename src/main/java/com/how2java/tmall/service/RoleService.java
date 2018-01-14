package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Role;
import com.how2java.tmall.pojo.RoleMenuVo;

import java.util.List;

public interface RoleService {
    List<Role> list();
    //List<Role> list(Page page);
    //int total();
    void add(RoleMenuVo roleMenuVo);
    void delete(Integer id);
    Role get(Integer id);
    void update(RoleMenuVo roleMenuVo);
    void fillMenus(Role role);
    void fillMenus(List<Role> roles);
    void fillAdminUsers(Role role);
    void fillAdminUsers(List<Role> roles);
}
