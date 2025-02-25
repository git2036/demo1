package com.example.demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class QuerySql {
    // 表单ID
    private Long formId;
    // 表单名称
    private String formName;
    // 数据源
    private DataSources datasource;
    // SQL查询语句
    private String sqlQuery;
    // 列配置
    private List<ColumnConfig> columnConfig;
}
