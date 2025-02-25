package com.example.demo.pojo;

import lombok.Data;

@Data
public class DataSources {
    private Integer dataSourceID;
    private String dataSourceName;
    private String dataSourceType;
    private String connectionInfo;
    private String dataSourceUsername;
    private String dataSourcePassword;
}
