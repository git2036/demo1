package com.example.demo.controller;

import com.example.demo.pojo.Result;
import com.example.demo.service.ReportGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

// 标记为 Spring 的 RESTful 控制器，处理与报表生成相关的 HTTP 请求
@RestController
// 定义请求的基础路径
@RequestMapping("/reportgenerator")
public class ReportGeneratorController {

    // 自动注入 ReportGeneratorService，用于生成报表数据
    @Autowired
    private ReportGeneratorService reportGeneratorService;

    // 处理 GET 请求，根据报表模板的 ID 生成报表数据
    @GetMapping("/generate/{templateId}")
    public Result generateReportData(@PathVariable("templateId") int templateId) {
        try {
            // 调用 ReportGeneratorService 的 generateReportData 方法生成报表数据
            List<Map<String, Object>> reportData = reportGeneratorService.generateReportData(templateId);
            // 返回成功响应，包含生成的报表数据
            return Result.success(reportData);
        } catch (Exception e) {
            // 如果生成报表数据过程中出现异常，返回错误响应，包含错误信息
            return Result.error("Failed to generate report data: " + e.getMessage());
        }
    }
}