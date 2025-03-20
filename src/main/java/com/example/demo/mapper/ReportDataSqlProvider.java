package com.example.demo.mapper;

import com.example.demo.pojo.ReportDataItem;

import java.util.Map;

// 动态 SQL 提供类
public class ReportDataSqlProvider {

    public String insertReportData(ReportDataItem reportDataItem) {
        StringBuilder sql = new StringBuilder("INSERT INTO report_data (template_id");
        StringBuilder values = new StringBuilder("VALUES (#{templateId}");

        if (reportDataItem.getData() != null && !reportDataItem.getData().isEmpty()) {
            for (Map.Entry<String, Object> entry : reportDataItem.getData().entrySet()) {
                sql.append(", ").append(entry.getKey());
                values.append(", #{data.").append(entry.getKey()).append("}");
            }
        }

        sql.append(") ").append(values).append(")");
        return sql.toString();
    }

    public String updateReportData(ReportDataItem reportDataItem) {
        if (reportDataItem.getData() == null || reportDataItem.getData().isEmpty()) {
            // 可以选择抛出异常或者返回空字符串
            throw new IllegalArgumentException("No data to update");
        }
        StringBuilder sql = new StringBuilder("UPDATE report_data SET ");
        StringBuilder conditions = new StringBuilder(" WHERE template_id = #{templateId}");

        int index = 0;
        for (Map.Entry<String, Object> entry : reportDataItem.getData().entrySet()) {
            if (index > 0) {
                sql.append(", ");
            }
            sql.append(entry.getKey()).append(" = #{data.").append(entry.getKey()).append("}");
            index++;
        }

        sql.append(conditions);
        return sql.toString();
    }

    public String deleteReportData(int templateId, Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder("DELETE FROM report_data WHERE template_id = ").append(templateId);

        if (condition != null && !condition.isEmpty()) {
            int index = 0;
            for (Map.Entry<String, Object> entry : condition.entrySet()) {
                if (index > 0) {
                    sql.append(" AND ");
                }
                sql.append(entry.getKey()).append(" = ").append(entry.getValue());
                index++;
            }
        }

        return sql.toString();
    }

    public String selectReportData(int templateId, Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder("SELECT * FROM report_data WHERE template_id = ").append(templateId);

        if (condition != null && !condition.isEmpty()) {
            int index = 0;
            for (Map.Entry<String, Object> entry : condition.entrySet()) {
                if (index > 0) {
                    sql.append(" AND ");
                }
                sql.append(entry.getKey()).append(" = ").append(entry.getValue());
                index++;
            }
        }

        return sql.toString();
    }
}
