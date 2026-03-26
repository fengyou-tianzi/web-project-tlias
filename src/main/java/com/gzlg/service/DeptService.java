package com.gzlg.service;

import com.gzlg.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 查新所有部门
     */
    List<Dept> findAll();
}
