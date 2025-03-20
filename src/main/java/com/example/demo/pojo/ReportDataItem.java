package com.example.demo.pojo;

import lombok.Data;

import java.util.Map;

@Data
public class ReportDataItem {
    private Integer templateId;
    private Map<String, Object> data;
}