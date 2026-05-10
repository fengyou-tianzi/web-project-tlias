package com.gzlg.controller;

import com.gzlg.pojo.Dept;
import com.gzlg.pojo.Result;
import com.gzlg.service.DeptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DeptController {

    private static final Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    /**
     * 查询所有部门
     */
    @GetMapping("/depts")
    public Result findAll() {
        log.info("收到查询所有部门的请求");
        List<Dept> deptList = deptService.findAll();
        log.info("查询完成，共查询到 {} 条部门数据", deptList.size());
        return Result.success(deptList);
    }

}
