package com.example.demo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data//生成getter、setter方法
@NoArgsConstructor//生成无参构造方法
@AllArgsConstructor//生成全参构造方法
@JsonIgnoreProperties(ignoreUnknown = true)//忽略未知属性
public class ReportTemplate {
    private Integer templateID;
    private String templateName;
    private Integer dataSourceID;
    private String querySql;
    private List<ColumnConfig> templateConfig;
    private String templateStart;
}