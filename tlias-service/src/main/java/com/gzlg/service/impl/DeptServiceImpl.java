package com.gzlg.service.impl;

import com.gzlg.exception.BusinessException;
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

    @Override
    public List<Dept> findAll() {
        log.debug("开始查询所有部门");
        List<Dept> deptList = deptMapper.findAll();
        log.debug("查询完成, 共查询到{}条记录", deptList.size());
        return deptList;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("开始删除部门, ID: {}", id);
        Integer empCount = deptMapper.countByDeptId(id);
        if (empCount != null && empCount > 0) {
            throw new BusinessException("对不起, 当前部门下有员工, 不能直接删除");
        }
        deptMapper.deleteById(id);
        log.debug("删除部门完成, ID: {}", id);
    }

    @Override
    public void save(Dept dept) {
        log.debug("开始保存部门, 部门名称: {}", dept.getName());
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.insert(dept);
        log.debug("保存部门完成, 生成的ID: {}", dept.getId());
    }

    @Override
    public Dept getById(Integer id) {
        log.debug("开始根据ID查询部门, ID: {}", id);
        Dept dept = deptMapper.getBbyId(id);
        log.debug("查询结果: {}", dept);
        return dept;
    }

    @Override
    public void update(Dept dept) {
        log.debug("开始修改部门信息, 部门ID: {}, 部门名称: {}", dept.getId(), dept.getName());
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
        log.debug("修改部门信息完成");
    }
}
