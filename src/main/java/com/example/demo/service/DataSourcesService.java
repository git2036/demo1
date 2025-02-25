package com.example.demo.service;

import com.example.demo.pojo.DataSources;

import java.util.List;

public interface DataSourcesService {
    
    //查找数据源
    DataSources findByDataSourceName(String dataSources);
    List<DataSources> findByDataSourceAllName(String dataSources);

    boolean AddDataSource(String dataSourceName, String dataSourceType, String connectionInfo, String dataSourceUsername, String dataSourcePassword);

    List<DataSources> getAllDatasources();

    void deleteDataSource(Integer id);

    void updateDataSource(Integer id, String dataSourceName, String dataSourceType, String connectionInfo, String dataSourceUsername, String dataSourcePassword);

    DataSources findByDataSourceID(Integer id);
}
