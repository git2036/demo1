package com.example.demo.service.impl;

import com.example.demo.mapper.DataSourcesMapper;
import com.example.demo.pojo.DBConnectionUtil;
import com.example.demo.pojo.DataSources;
import com.example.demo.service.DataSourcesService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class DataSourcesServiceImpl implements DataSourcesService {

    @Autowired
    private DataSourcesMapper DataSourcesMapper;

    // 查找数据源
    @Override
    public DataSources findByDataSourceName(String dataSources) {
        return  DataSourcesMapper.findByDataSourceName(dataSources);
    }

    public List<DataSources> findByDataSourceAllName(String dataSources) {
        return  DataSourcesMapper.findByDataSourceAllName(dataSources);
    }

    //插入数据源
    @Override
    public boolean AddDataSource(String dataSourceName, String dataSourceType, String connectionInfo, String dataSourceUsername, String dataSourcePassword) {
        DataSourcesMapper.AddDataSource(dataSourceName, dataSourceType, connectionInfo, dataSourceUsername, dataSourcePassword);
        return false;
    }

    @Override
    public List<DataSources> getAllDatasources() {
        return DataSourcesMapper.getAllDatasources();
    }

    @Override
    public void deleteDataSource(Integer id) {
        DataSourcesMapper.deleteDataSource(id);
    }

    @Override
    public void updateDataSource(Integer id, String dataSourceName, String dataSourceType, String connectionInfo, String dataSourceUsername, String dataSourcePassword) {
        DataSourcesMapper.updateDataSource(id, dataSourceName, dataSourceType, connectionInfo, dataSourceUsername, dataSourcePassword);
    }

    @Override
    public DataSources findByDataSourceID(Integer id) {
        String sql = "SELECT * FROM datasources WHERE DataSourceID = ?";
        try (Connection connection = DBConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    DataSources dataSources = new DataSources();
                    dataSources.setDataSourceID(resultSet.getInt("DataSourceID"));
                    dataSources.setDataSourceName(resultSet.getString("DataSourceName"));
                    dataSources.setDataSourceType(resultSet.getString("DataSourceType"));
                    dataSources.setConnectionInfo(resultSet.getString("ConnectionInfo"));
                    dataSources.setDataSourceUsername(resultSet.getString("DataSourceUsername"));
                    dataSources.setDataSourcePassword(resultSet.getString("DataSourcePassword"));
                    return dataSources;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
