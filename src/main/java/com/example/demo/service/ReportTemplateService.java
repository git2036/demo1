package com.example.demo.service;

import com.example.demo.pojo.ReportTemplate;

import java.util.List;

public interface ReportTemplateService {
    boolean saveReportTemplate(ReportTemplate reportTemplate);
    List<ReportTemplate> getAllReportTemplates();

    ReportTemplate getReportTemplateById(int id);

    boolean deleteReportTemplateById(int id);
}