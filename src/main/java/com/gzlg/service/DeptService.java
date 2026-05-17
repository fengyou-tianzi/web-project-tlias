package com.gzlg.service;

import com.gzlg.pojo.Dept;
import java.util.List;

/**
 * 部门管理接口
 */
public interface DeptService {
    /**
     * 查新所有部门
     */
    List<Dept> findAll();

    /**
     * 根据ID删除部门数据
     */
    void deleteById(Integer id);

    /**
     * 添加部门数据
     */
    void save(Dept dept);

    /**
     * 根据ID查询部门数据
     */
    Dept getById(Integer id);

    /**
     * 修改部门数据
     */
    void update(Dept dept);
}
