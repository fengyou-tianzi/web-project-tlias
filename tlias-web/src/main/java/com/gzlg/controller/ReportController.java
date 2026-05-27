package com.gzlg.controller;

import com.gzlg.common.Result;
import com.gzlg.service.EmpService;
import com.gzlg.service.StudentService;
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

    @Autowired
    private StudentService studentService;

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

    @GetMapping("/studentDegreeData")
    public Result studentDegreeData() {
        log.info("统计学员学历信息");
        List<Map<String, Object>> degreeList = studentService.getStudentDegreeData();
        return Result.success(degreeList);
    }

    @GetMapping("/studentCountData")
    public Result studentCountData() {
        log.info("统计班级学员人数");
        Map<String, Object> countData = studentService.getStudentCountData();
        return Result.success(countData);
    }
}
