package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.RoleMapper;
import com.how2java.tmall.pojo.Role;
import com.how2java.tmall.pojo.RoleMenuVo;
import com.how2java.tmall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
//    @Transactional(readOnly = true)
////    @Cacheable(value="role",key = "#root.targetClass.name.concat(#root.methodName).concat(#root.args[0]).concat(':').concat(#root.args[1])")
//    public List<Role> list(int start,int count) {
//        return roleMapper.selectRoleList();
//    }

    @Override
    @Transactional(readOnly = true)
    public List<Role> list() {
        return roleMapper.selectRoleList();
    }

    @Override
    @Transactional(readOnly = true)
    public Role get(Integer id) {
        Role role = roleMapper.selectByPrimaryKey(id);
        return role;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void add(RoleMenuVo roleMenuVo) {
        roleMapper.insertRole(roleMenuVo.getRole());
        //roleMenuVo中的role得到自增主键
        roleMenuVo.setRoleId(roleMenuVo.getRole().getId());
        roleMapper.insertRoleMenu(roleMenuVo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void delete(Integer id) {
        roleMapper.deleteRoleMenu(id);
        roleMapper.deleteRole(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED,rollbackFor=Exception.class)
    public void update(RoleMenuVo roleMenuVo) {
        int roleId = roleMenuVo.getRole().getId();
        roleMenuVo.setRoleId(roleId);
        roleMapper.updateRole(roleMenuVo.getRole());
        roleMapper.deleteRoleMenu(roleMenuVo.getRole().getId());
        roleMapper.insertRoleMenu(roleMenuVo);
    }

    @Override
    @Transactional(readOnly = true)
    public void fillMenus(Role role) {
        int roleId = role.getId();
        Role r = roleMapper.selectByPrimaryKey(roleId);
        role.setMenus(r.getMenus());
    }

    @Override
    public void fillMenus(List<Role> roles) {
        for (Role r:roles){
            fillMenus(r);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void fillAdminUsers(Role role) {
        int roleId = role.getId();
        Role r = this.get(roleId);
        role.setAdminUsers(r.getAdminUsers());
    }

    @Override
    public void fillAdminUsers(List<Role> roles) {
        for (Role r:roles){
            fillAdminUsers(r);
        }
    }
}
