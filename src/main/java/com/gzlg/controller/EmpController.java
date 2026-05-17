package com.gzlg.controller;

import com.gzlg.pojo.EmpQueryparam;
import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.Result;
import com.gzlg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 员工管理
 */
@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    /**
     * 条件分页查询
     */
    @GetMapping
    public Result page(EmpQueryparam empQueryparam) {
        PageResult pageResult = empService.page(empQueryparam);
        return Result.success(pageResult);
    }

}
