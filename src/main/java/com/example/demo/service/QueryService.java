package com.example.demo.service;

import java.util.Map;

public interface QueryService {
    /**
     * 执行查询操作
     * @param dataSourceId 数据源 ID
     * @param query 查询语句
     * @return 包含查询结果的 Map，键为 "columns" 和 "rows"
     */
    Map<String, Object> executeQuery(Integer dataSourceId, String query);
}