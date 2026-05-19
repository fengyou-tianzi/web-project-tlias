package com.gzlg.controller;

import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpQueryparam;
import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.Result;
import com.gzlg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        log.info("条件分页查询员工信息, 查询参数: {}", empQueryparam);
        PageResult pageResult = empService.page(empQueryparam);
        log.info("查询完成, 共{}条记录", pageResult.getTotal());
        return Result.success(pageResult);
    }

    /**
     * 添加员工
     */
    @PostMapping
    public Result sava(@RequestBody Emp emp) {
        empService.save(emp);
        return Result.success();
    }

}
