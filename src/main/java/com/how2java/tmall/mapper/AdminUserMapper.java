package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.pojo.UserRoleVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdminUserMapper {
    AdminUser selectByPrimaryKey(Integer id);

    List<AdminUser> selectAdminUserList();

    AdminUser selectByNamePassword(AdminUser adminUser);

    AdminUser findAdminUser(String username);

    Set<String> findRoles(String name);

    Set<String> findPermissions(String name);

    void insertAdminUser(AdminUser user);

    void insertAdminUserRole(UserRoleVo userRoleVo);

    void deleteAdminUser(int id);

    void deleteAdminUserRole(int uid);

    void deleteAdminUserList(List<Integer> uidList);

    void deleteAdminUserRoleList(List<Integer> uidList);

    void updateAdminUser(AdminUser adminUser);
}
