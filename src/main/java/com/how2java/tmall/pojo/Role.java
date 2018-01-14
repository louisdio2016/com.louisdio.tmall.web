package com.how2java.tmall.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Role {
    private int id;
    private String name;
    private List<Menu> menus;
    private List<AdminUser> adminUsers;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    //user_role表的主键
    private int urid;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public List<AdminUser> getAdminUsers() {
        return adminUsers;
    }

    public void setAdminUsers(List<AdminUser> adminUsers) {
        this.adminUsers = adminUsers;
    }

    public int getUrid() {
        return urid;
    }

    public void setUrid(int urid) {
        this.urid = urid;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menus=" + menus +
                ", adminUsers=" + adminUsers +
                ", urid=" + urid +
                '}';
    }
}
