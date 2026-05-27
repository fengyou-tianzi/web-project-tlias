package com.gzlg.controller;

import com.gzlg.common.PageResult;
import com.gzlg.common.Result;
import com.gzlg.pojo.Clazz;
import com.gzlg.pojo.ClazzQueryParam;
import com.gzlg.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result page(ClazzQueryParam clazzQueryParam) {
        log.info("条件分页查询班级信息, 查询参数: {}", clazzQueryParam);
        PageResult pageResult = clazzService.page(clazzQueryParam);
        log.info("查询完成, 共{}条记录", pageResult.getTotal());
        return Result.success(pageResult);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除班级, ID: {}", id);
        clazzService.deleteById(id);
        log.info("删除班级成功, ID: {}", id);
        return Result.success();
    }

    @PostMapping
    public Result save(@RequestBody Clazz clazz) {
        log.info("添加班级: {}", clazz);
        clazzService.save(clazz);
        log.info("添加班级成功");
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据ID查询班级, ID: {}", id);
        Clazz clazz = clazzService.getInfo(id);
        return Result.success(clazz);
    }

    @PutMapping
    public Result update(@RequestBody Clazz clazz) {
        log.info("更新班级信息: {}", clazz);
        clazzService.update(clazz);
        log.info("更新班级信息成功");
        return Result.success();
    }

    @GetMapping("/list")
    public Result listAll() {
        log.info("查询所有班级信息");
        List<Clazz> clazzList = clazzService.listAll();
        return Result.success(clazzList);
    }
}
