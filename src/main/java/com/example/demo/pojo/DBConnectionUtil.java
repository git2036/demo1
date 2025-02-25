package com.example.demo.pojo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
    // 数据库连接URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo1?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";
    // 数据库用户名
    private static final String DB_USER = "root";
    // 数据库密码
    private static final String DB_PASSWORD = "root";

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}