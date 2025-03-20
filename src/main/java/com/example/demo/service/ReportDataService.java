package com.example.demo.service;

import com.example.demo.pojo.ReportDataItem;

import java.util.List;
import java.util.Map;

public interface ReportDataService {

    boolean addReportData(ReportDataItem reportDataItem);

    boolean updateReportData(ReportDataItem reportDataItem);

    boolean deleteReportData(int templateId, Map<String, Object> condition);

    List<Map<String, Object>> getReportData(int templateId, Map<String, Object> condition);
}