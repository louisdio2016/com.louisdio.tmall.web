package com.how2java.tmall.pojo;

import java.util.List;

public class UserRoleVo {
    private int userId;
    private List<Integer> roleIds;

    private AdminUser adminUser;

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "UserRoleVo{" +
                "userId=" + userId +
                ", roleIds=" + roleIds +
                '}';
    }
}
