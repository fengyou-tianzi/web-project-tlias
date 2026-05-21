package com.gzlg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzlg.exception.BusinessException;
import com.gzlg.mapper.ClazzMapper;
import com.gzlg.pojo.Clazz;
import com.gzlg.pojo.ClazzQueryParam;
import com.gzlg.pojo.PageResult;
import com.gzlg.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 班级管理 Service 实现类
 */
@Slf4j
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    /**
     * 根据开课时间和结课时间计算班级状态
     */
    private String calculateStatus(LocalDate beginDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (now.isAfter(endDate)) {
            // 当前时间 > 结课时间 → 已结课
            return "已结课";
        } else if (now.isBefore(beginDate)) {
            // 当前时间 < 开课时间 → 未开班
            return "未开班";
        } else {
            // 在开课和结课之间 → 在读中
            return "在读中";
        }
    }

    /**
     * 条件分页查询班级列表
     */
    @Override
    public PageResult page(ClazzQueryParam clazzQueryParam) {
        // 使用PageHelper开启分页
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        // 查询班级列表（关联班主任姓名）
        Page<Clazz> page = (Page<Clazz>) clazzMapper.list(clazzQueryParam);
        // 为每条记录计算班级状态
        page.getResult().forEach(clazz -> {
            clazz.setStatus(calculateStatus(clazz.getBeginDate(), clazz.getEndDate()));
        });
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据ID删除班级
     */
    @Override
    public void deleteById(Integer id) {
        // 先检查该班级下是否有关联的学生
        Integer studentCount = clazzMapper.countStudentByClazzId(id);
        if (studentCount != null && studentCount > 0) {
            // 有学生则不允许删除，抛出业务异常
            throw new BusinessException("对不起, 该班级下有学生, 不能直接删除");
        }
        // 无关联学生，执行删除
        clazzMapper.deleteById(id);
    }

    /**
     * 添加班级
     */
    @Override
    public void save(Clazz clazz) {
        // 设置创建时间和修改时间
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    /**
     * 根据ID查询班级信息
     */
    @Override
    public Clazz getInfo(Integer id) {
        return clazzMapper.selectById(id);
    }

    /**
     * 更新班级信息
     */
    @Override
    public void update(Clazz clazz) {
        // 更新修改时间
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);
    }

    /**
     * 查询所有班级
     */
    @Override
    public List<Clazz> listAll() {
        return clazzMapper.listAll();
    }
}
