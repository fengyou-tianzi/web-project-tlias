package com.gzlg.controller;

import com.gzlg.common.Result;
import com.gzlg.pojo.Dept;
import com.gzlg.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @GetMapping
    public Result findAll() {
        log.info("查询所有部门");
        List<Dept> deptList = deptService.findAll();
        log.info("查询结果: 共{}条记录", deptList.size());
        return Result.success(deptList);
    }

    @DeleteMapping
    public Result delete(Integer id) {
        log.info("删除部门, ID: {}", id);
        deptService.deleteById(id);
        log.info("删除部门成功, ID: {}", id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Dept dept) {
        log.info("新增部门: {}", dept);
        deptService.save(dept);
        log.info("新增部门成功");
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("根据ID查询部门, ID: {}", id);
        Dept dept = deptService.getById(id);
        log.info("查询结果: {}", dept);
        return Result.success(dept);
    }

    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("修改部门信息: {}", dept);
        deptService.update(dept);
        log.info("修改部门信息成功");
        return Result.success();
    }
}
