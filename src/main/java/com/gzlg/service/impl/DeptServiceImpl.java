package com.gzlg.service.impl;

import com.gzlg.mapper.DeptMapper;
import com.gzlg.pojo.Dept;
import com.gzlg.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查寻所有部门
     */
    @Override
    public List<Dept> findAll() {
        List<Dept> deptList = deptMapper.findAll();
        return deptList;
    }

    /**
     * 根据ID删除部门数据
     */
    @Override
    public void deleteById(Integer id) {
        deptMapper.deleteById(id);
    }

    /**
     * 添加部门数据
     */
    @Override
    public void save(Dept dept) {
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
    }

    /**
     * 根据ID查询部门数据
     */
    @Override
    public Dept getById(Integer id) {
        return deptMapper.getBbyId(id);
    }

    /**
     * 修改部门数据
     */
    @Override
    public void update(Dept dept) {
       dept.setUpdateTime(LocalDateTime.now());
       deptMapper.update(dept);
    }

}
