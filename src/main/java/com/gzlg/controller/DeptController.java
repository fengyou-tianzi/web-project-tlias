package com.gzlg.controller;

import com.gzlg.pojo.Dept;
import com.gzlg.pojo.Result;
import com.gzlg.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询所有部门
     */
    @GetMapping("/depts")
    public Result findAll() {
        // 记录请求信息
        log.info("收到查询所有部门的请求");
        // 记录请求参数
        log.debug("请求参数：无");
        // 调用服务层查询
        List<Dept> deptList = deptService.findAll();
        // 记录查询结果
        log.info("查询完成，共查询到 {} 条部门数据", deptList.size());
        log.debug("查询结果：{}", deptList);
        return Result.success(deptList);
    }

    /**
     * 根据ID删除部门
     */
    @DeleteMapping("/depts/{id}")
    public Result deleteById(@PathVariable Integer id) {
        // 记录请求信息
        log.info("收到删除部门请求");
        // 记录请求参数
        log.debug("请求参数：部门ID = {}", id);
        // 调用服务层删除
        deptService.deleteById(id);
        // 记录删除结果
        log.info("删除成功，部门ID = {}", id);
        return Result.success("删除成功");
    }

    /**
     * 新增部门
     */
    @PostMapping("/depts")
    public Result add(@RequestBody Dept dept) {
        // 记录请求信息
        log.info("收到新增部门请求");
        // 记录请求参数
        log.debug("请求参数：{}", dept);
        // 调用服务层新增
        deptService.add(dept);
        // 记录新增结果
        log.info("新增成功，部门名称 = {}", dept.getName());
        return Result.success("新增成功");
    }

}
