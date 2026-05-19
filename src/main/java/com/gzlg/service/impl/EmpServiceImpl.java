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
}
