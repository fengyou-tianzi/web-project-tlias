package com.gzlg.controller;

import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.Result;
import com.gzlg.pojo.Student;
import com.gzlg.pojo.StudentQueryParam;
import com.gzlg.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学员管理
 */
@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 条件分页查询学员列表
     */
    @GetMapping
    public Result page(StudentQueryParam studentQueryParam) {
        log.info("条件分页查询学员信息, 查询参数: {}", studentQueryParam);
        PageResult pageResult = studentService.page(studentQueryParam);
        log.info("查询完成, 共{}条记录", pageResult.getTotal());
        return Result.success(pageResult);
    }

    /**
     * 批量删除学员
     */
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable String ids) {
        log.info("批量删除学员, IDs: {}", ids);
        studentService.deleteByIds(ids);
        log.info("批量删除学员成功");
        return Result.success();
    }

    /**
     * 添加学员
     */
    @PostMapping
    public Result save(@RequestBody Student student) {
        log.info("添加学员: {}", student);
        studentService.save(student);
        log.info("添加学员成功");
        return Result.success();
    }

    /**
     * 根据ID查询学员信息
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id) {
        log.info("根据ID查询学员, ID: {}", id);
        Student student = studentService.getInfo(id);
        return Result.success(student);
    }

    /**
     * 更新学员信息
     */
    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("更新学员信息: {}", student);
        studentService.update(student);
        log.info("更新学员信息成功");
        return Result.success();
    }

    /**
     * 违纪处理
     */
    @PutMapping("/violation/{id}/{score}")
    public Result violation(@PathVariable Integer id, @PathVariable Integer score) {
        log.info("违纪处理, 学员ID: {}, 扣分: {}", id, score);
        studentService.violation(id, score);
        log.info("违纪处理成功");
        return Result.success();
    }
}
