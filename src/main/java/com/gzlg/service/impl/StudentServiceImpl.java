package com.gzlg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzlg.mapper.StudentMapper;
import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.Student;
import com.gzlg.pojo.StudentQueryParam;
import com.gzlg.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 学员管理 Service 实现类
 */
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 条件分页查询学员列表
     */
    @Override
    public PageResult page(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());
        Page<Student> page = (Page<Student>) studentMapper.list(studentQueryParam);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 批量删除学员
     */
    @Override
    public void deleteByIds(String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        studentMapper.deleteByIds(idList);
    }

    /**
     * 添加学员
     */
    @Override
    public void save(Student student) {
        student.setViolationCount(0);
        student.setViolationScore(0);
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }

    /**
     * 根据ID查询学员信息
     */
    @Override
    public Student getInfo(Integer id) {
        return studentMapper.selectById(id);
    }

    /**
     * 更新学员信息
     */
    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateById(student);
    }

    /**
     * 违纪处理
     */
    @Override
    public void violation(Integer id, Integer score) {
        studentMapper.updateViolation(id, score);
    }
}
