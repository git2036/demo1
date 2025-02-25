package com.example.demo.controller;

import com.example.demo.pojo.DataSources;
import com.example.demo.pojo.Result;
import com.example.demo.service.DataSourcesService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/datasources")
public class DataSourcesController {

    @Autowired
    private DataSourcesService dataSourcesService;

    // 添加数据源
    @PostMapping("/addDataSource")
    public Result addDataSource(@RequestBody Map<String, String> dataSourceRequst) {

        String dataSourceName = dataSourceRequst.get("dataSourceName");
        String DataSourceType = dataSourceRequst.get("dataSourceType");
        String ConnectionInfo = dataSourceRequst.get("connectionInfo");
        String dataSourceUsername = dataSourceRequst.get("dataSourceUsername");
        String dataSourcePassword = dataSourceRequst.get("dataSourcePassword");

        boolean isSuccess = dataSourcesService.AddDataSource(dataSourceName, DataSourceType, ConnectionInfo,
                dataSourceUsername, dataSourcePassword);
        // if (isSuccess) {
        return Result.success("数据源添加成功");
        // } else {
        // return Result.error("数据源添加失败");
        // }
    }

    // 获取所有数据源
    @GetMapping("/getAllDatasource")
    public Result getAllDatasource() {
        return Result.success(dataSourcesService.getAllDatasources());
    }

    // 根据 DataSourceName 查询数据源
    @GetMapping("/getDataSourceByName/{name}")
    public Result getDataSourceByName(@PathVariable("name") String name) {
        List<DataSources> dataSources = dataSourcesService.findByDataSourceAllName(name);
        if (dataSources == null) {
            return Result.error("数据源不存在");
        } else {
            return Result.success(dataSources);
        }
    }

    // 删除数据源
    @DeleteMapping("/deleteDataSource/{id}")
    public Result deleteDataSource(@PathVariable("id") Integer id) {
        dataSourcesService.deleteDataSource(id);
        return Result.success();
    }

    // 更新数据源 根据 DataSourceID 更新
    @PutMapping("/updateDataSource/{id}")
    public Result updateDataSource(@PathVariable("id") Integer id, @RequestBody Map<String, String> dataSourceRequst) {
        String dataSourceName = dataSourceRequst.get("dataSourceName");
        String DataSourceType = dataSourceRequst.get("dataSourceType");
        String ConnectionInfo = dataSourceRequst.get("connectionInfo");
        String dataSourceUsername = dataSourceRequst.get("dataSourceUsername");
        String dataSourcePassword = dataSourceRequst.get("dataSourcePassword");

        DataSources dataSources = dataSourcesService.findByDataSourceName(dataSourceName);

        if (dataSources == null) {
            dataSourcesService.updateDataSource(id, dataSourceName, DataSourceType, ConnectionInfo, dataSourceUsername,
                    dataSourcePassword);
            return Result.success();
        } else {
            return Result.error("用户名已存在");
        }
    }

    // 测试数据源链接
    @PostMapping("/testDataSource")
    public Result testDataSource(@RequestBody Map<String, String> dataSourceRequest) {
        String jdbcUrl = dataSourceRequest.get("connectionInfo");
        String username = dataSourceRequest.get("username"); // 新增用户名参数
        String password = dataSourceRequest.get("password"); // 新增密码参数

        try {
            // 1. 显式加载驱动（JDBC 4.0+可省略）
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. 尝试建立连接
            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
                // 3. 验证连接有效性（可选）
                if (connection.isValid(2)) { // 2秒超时
                    return Result.success("数据库连接成功");
                } else {
                    return Result.error("数据库连接失败");
                }
            } catch (SQLException e) {
                return Result.error("数据库连接失败：" + e.getMessage());
            }

        } catch (ClassNotFoundException e) {
            return Result.error("找不到数据库驱动");
        }
    }

}
