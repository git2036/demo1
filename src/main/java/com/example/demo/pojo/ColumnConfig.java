package com.example.demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnConfig {
    // 属性
    private String prop;
    // 标签
    private String label;
    // 宽度
    private Integer width;
    // 排序
    private String sort;
}