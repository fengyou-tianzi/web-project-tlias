package com.gzlg.controller;

import com.gzlg.pojo.Dept;
import com.gzlg.pojo.Result;
import com.gzlg.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
