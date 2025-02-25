package com.example.demo.service.impl;

import com.example.demo.pojo.DataSources;
import com.example.demo.service.DataSourcesService;
import com.example.demo.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class QueryServiceImpl implements QueryService {

    private final DataSourcesService dataSourcesService;

    // 构造函数注入 DataSourcesService
    public QueryServiceImpl(DataSourcesService dataSourcesService) {
        this.dataSourcesService = dataSourcesService;
    }

    @Override
    public Map<String, Object> executeQuery(Integer dataSourceID, String query) {
        // 根据数据源ID获取数据源信息
        DataSources dataSourceInfo = dataSourcesService.findByDataSourceID(dataSourceID);
        // 如果数据源信息为空，则抛出异常
        if (dataSourceInfo == null) {
            throw new RuntimeException("Data source not found");
        }

        try (Connection connection = DriverManager.getConnection(dataSourceInfo.getConnectionInfo(),
                dataSourceInfo.getDataSourceUsername(), dataSourceInfo.getDataSourcePassword());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // 获取结果集的元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 获取结果集的列数
            int columnCount = metaData.getColumnCount();

            // 创建一个列表，用于存储列名
            List<String> columns = new ArrayList<>(columnCount);
            // 创建一个列表，用于存储列的详细信息
            List<Map<String, Object>> columnDetails = new ArrayList<>();

            // 遍历结果集的列，将列名添加到列表中，并获取列的详细信息
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnLabel(i);
                columns.add(columnName);

                // 获取列的详细信息
                Map<String, Object> columnInfo = new LinkedHashMap<>();
                columnInfo.put("columnName", columnName);
                columnInfo.put("columnType", metaData.getColumnTypeName(i));
                columnInfo.put("dataType", metaData.getColumnType(i));
                columnInfo.put("columnSize", metaData.getColumnDisplaySize(i));
                columnInfo.put("precision", metaData.getPrecision(i));
                columnInfo.put("columnComment", metaData.getColumnLabel(i)); // 注释信息可能需要根据数据库具体情况获取
                columnDetails.add(columnInfo);
            }

            // 创建一个列表，用于存储行数据
            List<Map<String, Object>> rows = new ArrayList<>();
            // 遍历结果集的行，将行数据添加到列表中
            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                // 遍历列名，将列名和对应的值添加到行数据中
                for (String column : columns) {
                    row.put(column, resultSet.getObject(column));
                }
                rows.add(row);
            }

            // 返回结果集的列名、行数据和列的详细信息
            return Map.of("columns", columns, "rows", rows, "columnDetails", columnDetails);
        } catch (SQLException e) {
            // 如果查询执行失败，则抛出异常
            throw new RuntimeException("Query execution failed", e);
        }
    }
}