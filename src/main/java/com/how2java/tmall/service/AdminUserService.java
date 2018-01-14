package com.how2java.tmall.service;

import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.pojo.UserRoleVo;

import java.util.List;
import java.util.Set;

public interface AdminUserService {
    List<AdminUser> list();
    AdminUser findByName(String username);
    //List<AdminUser> list(Page page);
    //int total();
    void add(UserRoleVo userRoleVo);
    void delete(Integer id);
    AdminUser get(Integer id);
    AdminUser get(String name,String password);
    void update(UserRoleVo userRoleVo);
    void fillRoles(AdminUser adminUser);
    void fillRoles(List<AdminUser> adminUserList);
    Set<String> findRoles(String username);
    Set<String> findPermissions(String username);
}
