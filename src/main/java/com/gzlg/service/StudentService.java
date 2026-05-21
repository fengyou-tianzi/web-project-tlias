package com.gzlg.service;

import com.gzlg.pojo.PageResult;
import com.gzlg.pojo.Student;
import com.gzlg.pojo.StudentQueryParam;

/**
 * 学员管理接口
 */
public interface StudentService {

    /**
     * 条件分页查询学员列表
     */
    PageResult page(StudentQueryParam studentQueryParam);

    /**
     * 批量删除学员
     */
    void deleteByIds(String ids);

    /**
     * 添加学员
     */
    void save(Student student);

    /**
     * 根据ID查询学员信息
     */
    Student getInfo(Integer id);

    /**
     * 更新学员信息
     */
    void update(Student student);

    /**
     * 违纪处理
     */
    void violation(Integer id, Integer score);
}
