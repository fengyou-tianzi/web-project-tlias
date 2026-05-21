package com.gzlg.service;

import com.gzlg.pojo.Clazz;
import com.gzlg.pojo.ClazzQueryParam;
import com.gzlg.pojo.PageResult;

import java.util.List;

/**
 * 班级管理接口
 */
public interface ClazzService {

    /**
     * 条件分页查询班级列表
     */
    PageResult page(ClazzQueryParam clazzQueryParam);

    /**
     * 根据ID删除班级
     */
    void deleteById(Integer id);

    /**
     * 添加班级
     */
    void save(Clazz clazz);

    /**
     * 根据ID查询班级信息
     */
    Clazz getInfo(Integer id);

    /**
     * 更新班级信息
     */
    void update(Clazz clazz);

    /**
     * 查询所有班级
     */
    List<Clazz> listAll();
}
