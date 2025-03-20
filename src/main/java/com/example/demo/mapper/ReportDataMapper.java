package com.example.demo.mapper;

import com.example.demo.pojo.ReportDataItem;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportDataMapper {

    // 添加数据
    @InsertProvider(type = ReportDataSqlProvider.class, method = "insertReportData")
    int insertReportData(ReportDataItem reportDataItem);

    // 更新数据
    @UpdateProvider(type = ReportDataSqlProvider.class, method = "updateReportData")
    int updateReportData(ReportDataItem reportDataItem);

    // 删除数据
    @DeleteProvider(type = ReportDataSqlProvider.class, method = "deleteReportData")
    int deleteReportData(int templateId, Map<String, Object> condition);

    // 根据模板 ID 和条件查询数据
    @SelectProvider(type = ReportDataSqlProvider.class, method = "selectReportData")
    List<Map<String, Object>> selectReportData(int templateId, Map<String, Object> condition);
}

// 动态 SQL 提供类
class ReportDataSqlProvider {

    public String insertReportData(ReportDataItem reportDataItem) {
        StringBuilder sql = new StringBuilder("INSERT INTO report_data (template_id");
        StringBuilder values = new StringBuilder("VALUES (#{templateId}");

        for (Map.Entry<String, Object> entry : reportDataItem.getData().entrySet()) {
            sql.append(", ").append(entry.getKey());
            values.append(", #{data.").append(entry.getKey()).append("}");
        }

        sql.append(") ").append(values).append(")");
        return sql.toString();
    }

    public String updateReportData(ReportDataItem reportDataItem) {
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

        int index = 0;
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            if (index > 0) {
                sql.append(" AND ");
            }
            sql.append(entry.getKey()).append(" = ").append(entry.getValue());
            index++;
        }

        return sql.toString();
    }

    public String selectReportData(int templateId, Map<String, Object> condition) {
        StringBuilder sql = new StringBuilder("SELECT * FROM report_data WHERE template_id = ").append(templateId);

        int index = 0;
        for (Map.Entry<String, Object> entry : condition.entrySet()) {
            if (index > 0) {
                sql.append(" AND ");
            }
            sql.append(entry.getKey()).append(" = ").append(entry.getValue());
            index++;
        }

        return sql.toString();
    }
}