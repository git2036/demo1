package com.example.demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class QuerySql {
    private Long formId;
    private String formName;
    private DataSources datasource;
    private String sqlQuery;
    private List<ColumnConfig> columnConfig;
}
