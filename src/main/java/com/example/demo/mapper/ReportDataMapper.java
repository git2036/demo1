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

