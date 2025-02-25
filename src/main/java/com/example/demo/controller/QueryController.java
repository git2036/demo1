package com.example.demo.controller;

import com.example.demo.pojo.ExecuteQueryRequest;
import com.example.demo.pojo.Result;
import com.example.demo.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/queries")
public class QueryController {

    private static final Logger logger = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    private QueryService queryService;

    // 执行查询
    @PostMapping("/executeQuery")
    public Result executeQuery(@RequestBody ExecuteQueryRequest request) {
        if (request.getDataSourceId() == null) {
            return Result.error("数据源ID不能为空");
        }
        try {
            Map<String, Object> result = queryService.executeQuery(request.getDataSourceId(), request.getQuery());
            return Result.success(result);
        } catch (Exception e) {
            return Result.error("执行查询出错: " + e.getMessage());
        }
    }
}