package com.example.demo.controller;

import com.example.demo.pojo.ReportDataItem;
import com.example.demo.pojo.Result;
import com.example.demo.service.ReportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reportdata")
public class ReportDataController {

    @Autowired
    private ReportDataService reportDataService;

    // 添加数据
    @PostMapping("/add")
    public Result addReportData(@RequestBody ReportDataItem reportDataItem) {
        try {
            // 检查 reportDataItem 是否为空
            if (reportDataItem == null) {
                return Result.error("传入的报表数据项为空");
            }
            // 检查 reportDataItem 的 data 是否为空
            if (reportDataItem.getData() == null || reportDataItem.getData().isEmpty()) {
                return Result.error("传入的报表数据为空，无法添加");
            }
            boolean isSuccess = reportDataService.addReportData(reportDataItem);
            if (isSuccess) {
                return Result.success("数据添加成功");
            } else {
                return Result.error("数据添加失败");
            }
        } catch (Exception e) {
            return Result.error("添加数据时出错: " + e.getMessage());
        }
    }

    // 更新数据
    @PutMapping("/update")
    public Result updateReportData(@RequestBody ReportDataItem reportDataItem) {
        try {
            // 检查 reportDataItem 是否为空
            if (reportDataItem == null) {
                return Result.error("传入的报表数据项为空");
            }
            // 检查 reportDataItem 的 data 是否为空
            if (reportDataItem.getData() == null || reportDataItem.getData().isEmpty()) {
                return Result.error("传入的报表数据为空，无法更新");
            }
            boolean isSuccess = reportDataService.updateReportData(reportDataItem);
            if (isSuccess) {
                return Result.success("数据更新成功");
            } else {
                return Result.error("数据更新失败");
            }
        } catch (Exception e) {
            return Result.error("更新数据时出错: " + e.getMessage());
        }
    }

    // 删除数据
    @DeleteMapping("/delete/{templateId}")
    public Result deleteReportData(@PathVariable("templateId") int templateId, @RequestBody Map<String, Object> condition) {
        try {
            // 检查 templateId 是否有效
            if (templateId <= 0) {
                return Result.error("无效的模板 ID，无法删除数据");
            }
            // 检查 condition 是否为空
            if (condition == null || condition.isEmpty()) {
                return Result.error("删除条件为空，无法删除数据");
            }
            boolean isSuccess = reportDataService.deleteReportData(templateId, condition);
            if (isSuccess) {
                return Result.success("数据删除成功");
            } else {
                return Result.error("数据删除失败");
            }
        } catch (Exception e) {
            return Result.error("删除数据时出错: " + e.getMessage());
        }
    }

    // 查询数据
    @GetMapping("/get/{templateId}")
    public Result getReportData(@PathVariable("templateId") int templateId, @RequestParam Map<String, Object> condition) {
        try {
            // 检查 templateId 是否有效
            if (templateId <= 0) {
                return Result.error("无效的模板 ID，无法查询数据");
            }
            // 检查 condition 是否为空
            if (condition == null || condition.isEmpty()) {
                return Result.error("查询条件为空，无法查询数据");
            }
            List<Map<String, Object>> reportData = reportDataService.getReportData(templateId, condition);
            return Result.success(reportData);
        } catch (Exception e) {
            return Result.error("查询数据时出错: " + e.getMessage());
        }
    }
}