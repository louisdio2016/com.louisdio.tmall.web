package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.AdminUserMapper;
import com.how2java.tmall.pojo.AdminUser;
import com.how2java.tmall.pojo.UserRoleVo;
import com.how2java.tmall.service.AdminUserService;
import com.how2java.tmall.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class AdminUserServiceImpl implements AdminUserService{
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private RoleService roleService;

    @Override
    public Set<String> findRoles(String username) {
        return adminUserMapper.findRoles(username);
    }

    @Override
    public Set<String> findPermissions(String username) {
        return adminUserMapper.findPermissions(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdminUser> list() {
        List<AdminUser> adminUsers = adminUserMapper.selectAdminUserList();
        return adminUsers;
    }

    @Override
    public AdminUser findByName(String username) {
        AdminUser adminUser = adminUserMapper.findAdminUser(username);
        roleService.fillMenus(adminUser.getRoles());
        return adminUser;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void add(UserRoleVo userRoleVo) {
        adminUserMapper.insertAdminUser(userRoleVo.getAdminUser());
        userRoleVo.setUserId(userRoleVo.getAdminUser().getId());
        adminUserMapper.insertAdminUserRole(userRoleVo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        adminUserMapper.deleteAdminUserRole(id);
        adminUserMapper.deleteAdminUser(id);
    }

    @Override
    @Transactional(readOnly = true)
    public AdminUser get(Integer id) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(id);
        return adminUser;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminUser get(String name, String password) {
        AdminUser adminUser = new AdminUser();
        adminUser.setName(name);
        adminUser.setPassword(password);
        AdminUser adminUser1 = adminUserMapper.selectByNamePassword(adminUser);
        return adminUser1;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void update(UserRoleVo userRoleVo) {
        adminUserMapper.updateAdminUser(userRoleVo.getAdminUser());
        adminUserMapper.deleteAdminUserRole(userRoleVo.getAdminUser().getId());
        adminUserMapper.insertAdminUserRole(userRoleVo);
    }

    @Override
    public void fillRoles(AdminUser adminUser) {
        AdminUser adminUser1 = adminUserMapper.selectByPrimaryKey(adminUser.getId());
        adminUser.setRoles(adminUser1.getRoles());
    }

    @Override
    public void fillRoles(List<AdminUser> adminUserList) {
        for (AdminUser adminUser:adminUserList){
            fillRoles(adminUser);
        }
    }
}
