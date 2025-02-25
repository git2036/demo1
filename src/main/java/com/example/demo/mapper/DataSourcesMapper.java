package com.example.demo.mapper;

import com.example.demo.pojo.DataSources;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface DataSourcesMapper{

    //根据 dataSourceName 模糊查找数据源
    @Select("select * from datasources where dataSourceName like concat('%',#{dataSourceName},'%')")
    List<DataSources> findByDataSourceAllName(String dataSourceName);

    //根据 dataSourceID 查找数据源
    @Select("select * from datasources where dataSourceName=#{dataSourceName}")
    DataSources findByDataSourceName(String dataSources);

    //插入数据源 
    @Insert("insert into datasources(dataSourceName,dataSourceType,connectionInfo,dataSourceUsername,dataSourcePassword) values(#{dataSourceName},#{dataSourceType},#{connectionInfo},#{dataSourceUsername},#{dataSourcePassword})")
    void AddDataSource(String dataSourceName, String dataSourceType, String connectionInfo, String dataSourceUsername, String dataSourcePassword);

    //获取所有数据源
    @Select("select * from datasources")
    List<DataSources> getAllDatasources();

    //根据 dataSourceID 删除数据源
    @Delete("delete from datasources where DataSourceID=#{id}")
    void deleteDataSource(Integer id);

    //根据 dataSourceID 更新数据源
    @Update("update datasources set dataSourceName=#{dataSourceName},dataSourceType=#{dataSourceType},connectionInfo=#{connectionInfo},dataSourceUsername=#{dataSourceUsername},dataSourcePassword=#{dataSourcePassword} where DataSourceID=#{id}")
    void updateDataSource(Integer id, String dataSourceName, String dataSourceType, String connectionInfo, String dataSourceUsername, String dataSourcePassword);
}
