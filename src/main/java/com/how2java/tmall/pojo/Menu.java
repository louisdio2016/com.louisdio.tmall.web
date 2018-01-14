package com.how2java.tmall.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Menu {
    private int id;
    private String name;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    private String sign;
    private List<Role> roles;



    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }


    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createtime=" + createtime +
                ", sign='" + sign + '\'' +
                ", roles=" + roles +
                '}';
    }
}
