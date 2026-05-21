package com.gzlg.controller;

import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.Result;
import com.gzlg.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志管理
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    private OperateLogService operateLogService;

    /**
     * 分页查询操作日志
     */
    @GetMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("分页查询操作日志, page: {}, pageSize: {}", page, pageSize);
        PageResult pageResult = operateLogService.page(page, pageSize);
        log.info("查询完成, 共{}条记录", pageResult.getTotal());
        return Result.success(pageResult);
    }
}
