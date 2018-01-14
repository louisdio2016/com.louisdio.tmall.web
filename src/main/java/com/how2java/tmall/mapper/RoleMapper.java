package com.how2java.tmall.mapper;

import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.pojo.Role;
import com.how2java.tmall.pojo.RoleMenuVo;
import com.how2java.tmall.pojo.UserRoleVo;

import java.util.List;

public interface RoleMapper {
    Role selectByPrimaryKey(Integer id);

    List<Role> selectRoleList();

    void insertRole(Role role);

    void insertRoleMenu(RoleMenuVo roleMenuVo);

    void deleteRole(Integer id);

    void deleteRoleMenu(Integer rid);

    void deleteRoleList(List<Integer> ridList);

    void deleteRoleMenuList(List<Integer> ridList);

    void updateRole(Role user);
}
