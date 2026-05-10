package com.gzlg.service.impl;

import com.gzlg.mapper.DeptMapper;
import com.gzlg.pojo.Dept;
import com.gzlg.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询所有部门
     */
    @Override
    public List<Dept> findAll() {
        log.info("开始查询所有部门");
        List<Dept> deptList = deptMapper.findAll();
        log.info("查询完成，共查询到 {} 条部门数据", deptList.size());
        return deptList;
    }

    /**
     * 根据ID删除部门
     */
    @Override
    public void deleteById(Integer id) {
        log.info("开始删除部门，ID = {}", id);
        deptMapper.deleteById(id);
        log.info("删除完成，ID = {}", id);
    }

    /**
     * 新增部门
     */
    @Override
    public void add(Dept dept) {
        log.info("开始新增部门，部门名称 = {}", dept.getName());
        deptMapper.add(dept);
        log.info("新增完成");
    }
}
