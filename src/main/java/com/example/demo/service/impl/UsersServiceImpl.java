package com.example.demo.service.impl;

import com.example.demo.pojo.Users;
import com.example.demo.mapper.UsersMapper;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// 标识该类为一个服务类
@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    public Users findByUsername(String username) {
        return usersMapper.findByUsername(username);
    }

    @Override
    public void register(String username, String password) {
        usersMapper.add(username, password);
    }

    @Override
    public Users findById(int id) {
        return usersMapper.findByUserId(id);
    }

    @Override
    public void updateUserPassword(int id, String password) {
        usersMapper.updateUserPassword(id, password);
    }

    @Override
    public void deleteUser(int id) {
        usersMapper.deleteUser(id);
    }

    @Override
    public void updateUserDataAndPermissions(int id, String username, String password, String Permissions) {
        usersMapper.updateUserDataAndPermissions(id, username,  password,Permissions);
    }

    @Override
    public List<Users> findAllUsers() {
        return usersMapper.findAllUsers();
    }

    @Override
    public void updateUser(Users user) {
        usersMapper.updateUser(user);
    }

    @Override
    public void addUser(Users user) {
        usersMapper.addUser(user);
    }
}