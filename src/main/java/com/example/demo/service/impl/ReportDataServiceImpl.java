package com.example.demo.service.impl;

import com.example.demo.mapper.ReportDataMapper;
import com.example.demo.pojo.ReportDataItem;
import com.example.demo.service.ReportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportDataServiceImpl implements ReportDataService {

    @Autowired
    private ReportDataMapper reportDataMapper;

    @Override
    public boolean addReportData(ReportDataItem reportDataItem) {
        int rows = reportDataMapper.insertReportData(reportDataItem);
        return rows > 0;
    }

    @Override
    public boolean updateReportData(ReportDataItem reportDataItem) {
        int rows = reportDataMapper.updateReportData(reportDataItem);
        return rows > 0;
    }

    @Override
    public boolean deleteReportData(int templateId, Map<String, Object> condition) {
        int rows = reportDataMapper.deleteReportData(templateId, condition);
        return rows > 0;
    }

    @Override
    public List<Map<String, Object>> getReportData(int templateId, Map<String, Object> condition) {
        return reportDataMapper.selectReportData(templateId, condition);
    }
}