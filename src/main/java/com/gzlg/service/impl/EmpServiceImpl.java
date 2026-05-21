package com.gzlg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzlg.mapper.EmpExprMapper;
import com.gzlg.mapper.EmpMapper;
import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpExpr;
import com.gzlg.pojo.EmpQueryparam;
import com.gzlg.pojo.PageResult;
import com.gzlg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工管理实现类
 */
@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    /**
     * 条件分页查询
     */
    @Override
    public PageResult page(EmpQueryparam empQueryparam) {
        log.debug("开始条件分页查询员工, page: {}, pageSize: {}, 查询条件: {}",
                empQueryparam.getPage(), empQueryparam.getPageSize(), empQueryparam);
        PageHelper.startPage(empQueryparam.getPage(), empQueryparam.getPageSize());
        Page<Emp> page = (Page<Emp>) empMapper.list(empQueryparam);
        log.debug("查询完成, 总记录数: {}, 当前页记录数: {}", page.getTotal(), page.size());
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 添加员工
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {

        //添加员工基本信息
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        //批量添加员工经历信息
        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }

    }

    /**
     * 根据ID删除员工（含经历信息）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        log.debug("开始删除员工, ID: {}", ids);
        //删除员工基本信息
        empMapper.deleteByIds(ids);
        //删除员工经历信息
        empExprMapper.deleteByEmpIds(ids);
        log.debug("删除员工完成, ID: {}", ids);
    }

    /**
     * 根据id查询员工基本信息和工作经历
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Emp getInfo(Integer id) {
        Emp emp = empMapper.selectById(id);
        if (emp != null) {
            emp.setExprList(empExprMapper.selectByEmpId(id));
        }
        return emp;
    }

    /**
     * 更新员工信息（基础信息+工作经历）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        //更新员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        //先删除该员工所有工作经历
        empExprMapper.deleteByEmpIds(List.of(emp.getId()));

        //再批量插入新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            exprList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(exprList);
        }
    }

    /**
     * 查询所有员工
     */
    @Override
    public List<Emp> listAll() {
        return empMapper.listAll();
    }

}
