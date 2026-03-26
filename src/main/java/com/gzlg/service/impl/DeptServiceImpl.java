package com.gzlg.service.impl;

import com.gzlg.mapper.DeptMapper;
import com.gzlg.pojo.Dept;
import com.gzlg.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查寻所有部门
     */
    @Override
    public List<Dept> findAll() {
        return deptMapper.findAll();
    }
}
