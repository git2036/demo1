package com.example.demo.service.impl;

import com.example.demo.mapper.DataSourcesMapper;
import com.example.demo.mapper.ReportTemplateMapper;
import com.example.demo.pojo.DataSources;
import com.example.demo.pojo.ReportTemplate;
import com.example.demo.service.ReportGeneratorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

// 标记为 Spring 的服务类，实现 ReportGeneratorService 接口
@Service
public class ReportGeneratorServiceImpl implements ReportGeneratorService {

    // 自动注入 ReportTemplateMapper，用于操作报表模板信息
    @Autowired
    private ReportTemplateMapper reportTemplateMapper;

    // 自动注入 DataSourcesMapper，用于操作数据源信息
    @Autowired
    private DataSourcesMapper dataSourcesMapper;

    @Override
    public List<Map<String, Object>> generateReportData(int templateId) {
        // 根据模板 ID 从数据库中获取报表模板信息
        ReportTemplate reportTemplate = reportTemplateMapper.getReportTemplateById(templateId);
        // 如果报表模板信息不存在，抛出运行时异常
        if (reportTemplate == null) {
            throw new RuntimeException("Report template not found");
        }

        // 根据报表模板中的数据源 ID 从数据库中获取数据源信息
        DataSources dataSource = dataSourcesMapper.findByDataSourceID(reportTemplate.getDataSourceID());
        // 如果数据源信息不存在，抛出运行时异常
        if (dataSource == null) {
            throw new RuntimeException("Data source not found");
        }

        try (
                // 建立与数据源的数据库连接
                Connection connection = DriverManager.getConnection(dataSource.getConnectionInfo(),
                        dataSource.getDataSourceUsername(), dataSource.getDataSourcePassword());
                // 创建一个 Statement 对象，用于执行 SQL 语句
                Statement statement = connection.createStatement();
                // 执行报表模板中的查询 SQL 语句，获取结果集
                ResultSet resultSet = statement.executeQuery(reportTemplate.getQuerySql())
        ) {
            // 解析报表模板的配置信息，获取需要展示的字段列表
            List<String> fields = parseTemplateConfig(reportTemplate.getTemplateConfig());
            System.out.println(fields);

            // 用于存储查询结果的行数据
            List<Map<String, Object>> rows = new ArrayList<>();
            // 遍历结果集的每一行
            while (resultSet.next()) {
                // 存储当前行的数据
                Map<String, Object> row = new LinkedHashMap<>();
                // 遍历需要展示的字段列表
                for (String field : fields) {
                    // 将当前行中指定字段的值添加到 row 中
                    row.put(field, resultSet.getObject(field));
                }
                // 将当前行的数据添加到 rows 列表中
                rows.add(row);
            }

            return rows;
        } catch (Exception e) {
            // 如果查询执行过程中出现异常，抛出运行时异常
            throw new RuntimeException("Query execution failed", e);
        }
    }

    private List<String> parseTemplateConfig(String templateConfig) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // 修改 TypeReference 为 List<Map<String, String>>
            List<Map<String, String>> configList = objectMapper.readValue(templateConfig, new TypeReference<List<Map<String, String>>>() {});
            List<String> fields = new ArrayList<>();
            for (Map<String, String> map : configList) {
                if (map.containsKey("prop")) {
                    fields.add(map.get("prop"));
                }
            }
            return fields;
        } catch (JsonProcessingException e) {
            // 记录日志
            System.err.println("Failed to parse template config: " + templateConfig);
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}