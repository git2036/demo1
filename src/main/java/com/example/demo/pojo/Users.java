package com.example.demo.pojo;

import lombok.Data;

//lombok注解 在编译时自动生成getter/setter方法
@Data
//用户实体类
public class Users {
    private Integer userID;    // 用户唯一标识
    private String username;   // 用户名
    private String password;   // 用户密码
    private String Permissions;  // 用户角色权限
}