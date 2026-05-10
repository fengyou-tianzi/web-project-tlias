package com.gzlg.service;

import com.gzlg.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 查询所有部门
     */
    List<Dept> findAll();

    /**
     * 根据ID删除部门
     */
    void deleteById(Integer id);

    /**
     * 新增部门
     */
    void add(Dept dept);
}
