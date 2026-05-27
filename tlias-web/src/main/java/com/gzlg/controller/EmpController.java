package com.gzlg.controller;

import com.gzlg.common.PageResult;
import com.gzlg.common.Result;
import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpQueryparam;
import com.gzlg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {

    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(EmpQueryparam empQueryparam) {
        log.info("条件分页查询员工信息, 查询参数: {}", empQueryparam);
        PageResult pageResult = empService.page(empQueryparam);
        log.info("查询完成, 共{}条记录", pageResult.getTotal());
        return Result.success(pageResult);
    }

    @PostMapping
    public Result sava(@RequestBody Emp emp) {
        empService.save(emp);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除员工, ID: {}", ids);
        empService.deleteByIds(ids);
        log.info("删除员工成功, ID: {}", ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        Emp emp = empService.getInfo(id);
        return Result.success(emp);
    }

    @PutMapping
    public Result update(@RequestBody Emp emp){
        empService.update(emp);
        return Result.success();
    }

    @GetMapping("/list")
    public Result listAll() {
        log.info("查询全部员工信息");
        List<Emp> empList = empService.listAll();
        return Result.success(empList);
    }
}
