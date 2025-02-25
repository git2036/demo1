package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data//生成getter、setter方法、toString方法、equals方法、hashCode方法
@JsonIgnoreProperties(ignoreUnknown = true)//忽略未知属性
public class ReportTemplate {
    private Integer templateID;
    private String templateName;
    private Integer dataSourceID;
    private String TemplateCreator;
    private String querySql;
    private String templateConfig;
    private String templateState;
}