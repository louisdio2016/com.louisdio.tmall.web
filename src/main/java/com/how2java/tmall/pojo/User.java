package com.how2java.tmall.pojo;

public class User {
    private Integer id;

    private String name;

    private String password;

    public String getAnonyous(){
        String theName = this.name;
        if ("".equals(theName))
            return "";
        if (theName == null)
            return null;
        if (2 == theName.length())
            return theName.substring(0,1)+"*";
        char[] chars = theName.toCharArray();
        for (int i=1;i<chars.length-1;i++){
            chars[i] = '*';
        }
        return new String(chars);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}