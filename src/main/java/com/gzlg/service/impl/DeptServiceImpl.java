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
     * 查寻所有部门
     */
    @Override
    public List<Dept> findAll() {
        log.info("开始查询所有部门");
        List<Dept> deptList = deptMapper.findAll();
        log.info("查询完成，共查询到 {} 条部门数据", deptList.size());
        return deptList;
    }
}
