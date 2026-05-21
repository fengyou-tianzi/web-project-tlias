package com.gzlg.controller;

import com.gzlg.pojo.Result;
import com.gzlg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private EmpService empService;

    @GetMapping("/empGenderData")
    public Result empGenderData() {
        log.info("统计员工性别信息");
        List<Map<String, Object>> genderList = empService.getEmpGenderData();
        return Result.success(genderList);
    }

    @GetMapping("/empJobData")
    public Result empJobData() {
        log.info("统计员工职位人数");
        Map<String, Object> jobData = empService.getEmpJobData();
        return Result.success(jobData);
    }
}
