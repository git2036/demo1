package com.example.demo.mapper;

import com.example.demo.pojo.ReportTemplate;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReportTemplateMapper {
    //插入 TemplateName DataSourceID TemplateCreator QuerySql TemplateConfig TemplateState
    @Insert("INSERT INTO reporttemplates (TemplateName, DataSourceID, TemplateCreator, QuerySql, TemplateConfig, TemplateState) VALUES (#{templateName}, #{dataSourceID}, #{templateCreator}, #{querySql}, #{templateConfig}, #{templateState})")
    int insertReportTemplate(ReportTemplate reportTemplate);

    @Select("SELECT * FROM reporttemplates")
    List<ReportTemplate> getAllReportTemplates();

    @Select("SELECT * FROM reporttemplates WHERE TemplateID = #{id}")
    ReportTemplate getReportTemplateById(int id);

    @Delete("DELETE FROM reporttemplates WHERE TemplateID = #{id}")
    int deleteReportTemplateById(int id);

    //更新TemplateName templateState
    @Update("UPDATE reporttemplates SET TemplateName = #{templateName}, TemplateState = #{templateState} WHERE TemplateID = #{id}")
    int updateReportTemplate(int id);
}