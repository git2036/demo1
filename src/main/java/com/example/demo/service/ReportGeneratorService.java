package com.example.demo.service;

import com.example.demo.pojo.ReportTemplate;
import java.util.List;
import java.util.Map;

// 报表生成服务接口，定义了生成报表数据的方法
public interface ReportGeneratorService {
    // 根据报表模板的 ID 生成报表数据，返回一个包含报表数据的列表
    List<Map<String, Object>> generateReportData(int templateId);
}