package com.example.demo.mapper;

import com.example.demo.pojo.Users;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UsersMapper {

    // 查询用户名
    @Select("select * from users where username=#{username}")
    Users findByUsername(String username);

    //插入用户
    @Insert("insert into users(username,password) values(#{username},#{password})")
    void add(String username, String password);

    //查询用户信息
    @Select("select * from users where UserID=#{id}")
    Users findByUserId(int id);

    //更新用户密码
    @Update("update users set password=#{password} where UserID=#{id}")
    void updateUserPassword(int id, String password);

    //删除用户
    @Delete("delete from users where UserID=#{id}")
    void deleteUser(int id);

    //更新用户信息和权限
    @Update("update users set username=#{username},password=#{password},Permissions=#{permissions} where UserID=#{id}")
    void updateUserDataAndPermissions(int id, String username, String password, String permissions);

    //查询所有用户信息
    @Select("select * from users")
    List<Users> findAllUsers();

    //更新用户信息
    @Update("update users set username=#{username},password=#{password},Permissions=#{permissions} where UserID=#{userID}")
    void updateUser(Users user);

    //新增用户
    @Insert("insert into users(username,password,Permissions) values(#{username},#{password},#{permissions})")
    void addUser(Users user);


}