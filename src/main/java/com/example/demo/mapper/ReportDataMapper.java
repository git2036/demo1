package com.example.demo.mapper;

import com.example.demo.config.SqlParser;
import com.example.demo.pojo.ReportDataItem;
import com.example.demo.service.ReportTemplateService;
import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
@Component
class ReportDataSqlProvider {

    @Autowired
    private ReportTemplateService reportTemplateService;

    public String insertReportData(ReportDataItem reportDataItem) {
        int templateId = reportDataItem.getTemplateId();
        String tableName = getTableNameFromTemplate(templateId);

        StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (template_id");
        StringBuilder values = new StringBuilder("VALUES (#{templateId}");

        for (Map.Entry<String, Object> entry : reportDataItem.getData().entrySet()) {
            sql.append(", ").append(entry.getKey());
            values.append(", #{data.").append(entry.getKey()).append("}");
        }

        sql.append(") ").append(values).append(")");
        return sql.toString();
    }

    public String updateReportData(ReportDataItem reportDataItem) {
        int templateId = reportDataItem.getTemplateId();
        String tableName = getTableNameFromTemplate(templateId);

        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
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
        String tableName = getTableNameFromTemplate(templateId);

        StringBuilder sql = new StringBuilder("DELETE FROM ").append(tableName).append(" WHERE template_id = ").append(templateId);

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
        String tableName = getTableNameFromTemplate(templateId);

        StringBuilder sql = new StringBuilder("SELECT * FROM ").append(tableName).append(" WHERE template_id = ").append(templateId);

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

    private String getTableNameFromTemplate(int templateId) {
        // 根据 templateId 获取报表模板信息
        com.example.demo.pojo.ReportTemplate reportTemplate = reportTemplateService.getReportTemplateById(templateId);
        if (reportTemplate != null) {
            String querySql = reportTemplate.getQuerySql();
            return SqlParser.getTableNameFromSql(querySql);
        }
        return "report_data"; // 默认表名
    }
}

