package com.example.demo.service.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.pojo.DataSources;
import com.example.demo.service.DataSourcesService;
import com.example.demo.service.QueryService;

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
            // 获取数据库元数据
            DatabaseMetaData dbMetaData = connection.getMetaData();
            // 获取结果集的列数
            int columnCount = metaData.getColumnCount();

            // 创建一个列表，用于存储列信息
            List<Map<String, Object>> columnsInfo = new ArrayList<>(columnCount);
            // 遍历结果集的列，将列信息添加到列表中
            for (int i = 1; i <= columnCount; i++) {
                Map<String, Object> columnInfo = new LinkedHashMap<>();
                columnInfo.put("columnName", metaData.getColumnName(i));
                columnInfo.put("columnLabel", metaData.getColumnLabel(i));
                columnInfo.put("columnType", metaData.getColumnTypeName(i));
                columnInfo.put("dataType", metaData.getColumnType(i));
                columnInfo.put("columnSize", metaData.getColumnDisplaySize(i));
                columnInfo.put("precision", metaData.getPrecision(i));

                // 获取字段注释
                ResultSet columns = dbMetaData.getColumns(null, null, metaData.getTableName(i), metaData.getColumnName(i));
                if (columns.next()) {
                    columnInfo.put("remarks", columns.getString("REMARKS"));
                } else {
                    columnInfo.put("remarks", "");
                }
                columns.close();

                columnsInfo.add(columnInfo);
            }

            // 创建一个列表，用于存储行数据
            List<Map<String, Object>> rows = new ArrayList<>();
            // 遍历结果集的行，将行数据添加到列表中
            while (resultSet.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnLabel(i), resultSet.getObject(i));
                }
                rows.add(row);
            }

            // 返回结果集的列信息和行数据
            return Map.of("columnsInfo", columnsInfo, "rows", rows);
        } catch (Exception e) {
            // 如果查询执行失败，则抛出异常
            throw new RuntimeException("Query execution failed", e);
        }
    }
}