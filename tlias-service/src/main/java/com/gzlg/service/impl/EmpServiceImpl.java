package com.gzlg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzlg.common.PageResult;
import com.gzlg.exception.BusinessException;
import com.gzlg.mapper.EmpExprMapper;
import com.gzlg.mapper.EmpMapper;
import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpExpr;
import com.gzlg.pojo.EmpQueryparam;
import com.gzlg.pojo.LoginInfo;
import com.gzlg.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Override
    public PageResult page(EmpQueryparam empQueryparam) {
        log.debug("开始条件分页查询员工, page: {}, pageSize: {}, 查询条件: {}",
                empQueryparam.getPage(), empQueryparam.getPageSize(), empQueryparam);
        PageHelper.startPage(empQueryparam.getPage(), empQueryparam.getPageSize());
        Page<Emp> page = (Page<Emp>) empMapper.list(empQueryparam);
        log.debug("查询完成, 总记录数: {}, 当前页记录数: {}", page.getTotal(), page.size());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(Emp emp) {
        log.debug("开始添加员工, 姓名: {}", emp.getName());
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        Integer empId = emp.getId();
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            log.debug("批量添加员工经历, 员工ID: {}, 经历数量: {}", empId, exprList.size());
            exprList.forEach(empExpr -> empExpr.setEmpId(empId));
            empExprMapper.insertBatch(exprList);
        }
        log.debug("添加员工完成, 员工ID: {}", empId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        log.debug("开始删除员工, ID: {}", ids);
        empMapper.deleteByIds(ids);
        empExprMapper.deleteByEmpIds(ids);
        log.debug("删除员工完成, ID: {}", ids);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Emp getInfo(Integer id) {
        log.debug("开始查询员工详情, ID: {}", id);
        Emp emp = empMapper.selectById(id);
        if (emp != null) {
            emp.setExprList(empExprMapper.selectByEmpId(id));
            log.debug("查询员工详情完成, ID: {}, 包含{}条工作经历", id, emp.getExprList().size());
        } else {
            log.debug("未找到ID为{}的员工", id);
        }
        return emp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        log.debug("开始更新员工信息, ID: {}", emp.getId());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        empExprMapper.deleteByEmpIds(List.of(emp.getId()));

        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)) {
            log.debug("重新插入员工工作经历, 员工ID: {}, 经历数量: {}", emp.getId(), exprList.size());
            exprList.forEach(empExpr -> {
                empExpr.setEmpId(emp.getId());
            });
            empExprMapper.insertBatch(exprList);
        }
        log.debug("更新员工信息完成, ID: {}", emp.getId());
    }

    @Override
    public List<Emp> listAll() {
        log.debug("开始查询所有员工");
        List<Emp> list = empMapper.listAll();
        log.debug("查询所有员工完成, 共{}条记录", list.size());
        return list;
    }

    @Override
    public List<Map<String, Object>> getEmpGenderData() {
        log.debug("开始统计员工性别信息");
        List<Map<String, Object>> genderList = empMapper.countByGender();
        log.debug("员工性别统计完成, 共{}种性别", genderList.size());
        return genderList;
    }

    @Override
    public Map<String, Object> getEmpJobData() {
        log.debug("开始统计员工职位人数");
        List<Map<String, Object>> jobList = empMapper.countByJob();
        Map<String, Object> result = new HashMap<>();
        result.put("jobList", jobList.stream().map(m -> m.get("name")).collect(Collectors.toList()));
        result.put("dataList", jobList.stream().map(m -> m.get("value")).collect(Collectors.toList()));
        log.debug("员工职位统计完成, 共{}种职位", jobList.size());
        return result;
    }

    @Override
    public LoginInfo login(Emp emp) {
        log.debug("开始登录, 用户名: {}", emp.getUsername());
        Emp loginEmp = empMapper.getByUsernameAndPassword(emp.getUsername(), emp.getPassword());
        if (loginEmp == null) {
            throw new BusinessException("用户名或密码错误");
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(loginEmp.getId());
        loginInfo.setUsername(loginEmp.getUsername());
        loginInfo.setName(loginEmp.getName());
        loginInfo.setToken(UUID.randomUUID().toString().replace("-", ""));
        log.debug("登录成功, 用户名: {}, 姓名: {}", loginEmp.getUsername(), loginEmp.getName());
        return loginInfo;
    }
}
