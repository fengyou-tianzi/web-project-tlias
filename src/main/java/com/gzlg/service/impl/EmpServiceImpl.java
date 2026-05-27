package com.gzlg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzlg.mapper.EmpExprMapper;
import com.gzlg.mapper.EmpMapper;
import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpExpr;
import com.gzlg.pojo.EmpQueryparam;
import com.gzlg.pojo.LoginInfo;
import com.gzlg.pojo.PageResult;
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

/**
 * 员工管理 Service 实现类
 * <p>
 * 提供员工的增删改查业务逻辑，包含员工基本信息和关联的工作经历信息的操作。
 * 涉及多表操作的方法使用 @Transactional 保证事务一致性。
 *
 * @author gzlg
 */
@Slf4j
@Service
public class EmpServiceImpl implements EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    /**
     * 条件分页查询员工列表
     * <p>
     * 使用 PageHelper 插件实现分页，自动拦截 SQL 添加 LIMIT 语句
     *
     * @param empQueryparam 分页查询参数（页码、每页条数、筛选条件）
     * @return 分页结果，包含总记录数和当前页数据
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
     * 添加员工（含工作经历）
     * <p>
     * 事务操作：先插入员工基本信息获取自增ID，再批量插入关联的工作经历。
     * 任何一步失败将回滚全部操作。
     *
     * @param emp 员工信息（包含工作经历列表）
     */
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

    /**
     * 根据ID批量删除员工（含工作经历）
     * <p>
     * 事务操作：先删除员工基本信息，再删除关联的工作经历信息。
     * 删除顺序：先删子表（经历），再删主表（员工），此处因无外键约束先删主表也可
     *
     * @param ids 待删除的员工ID列表
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Integer> ids) {
        log.debug("开始删除员工, ID: {}", ids);
        empMapper.deleteByIds(ids);
        empExprMapper.deleteByEmpIds(ids);
        log.debug("删除员工完成, ID: {}", ids);
    }

    /**
     * 根据ID查询员工详情（含工作经历）
     * <p>
     * 分两次查询：先查员工基本信息，再查关联的工作经历列表，组装后返回
     *
     * @param id 员工ID
     * @return 员工完整信息（含工作经历），不存在时返回 null
     */
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

    /**
     * 更新员工信息（含工作经历）
     * <p>
     * 事务操作：先更新员工基本信息，再采用"先删后增"策略更新工作经历：
     * 1. 删除该员工所有旧的工作经历
     * 2. 批量插入新的工作经历
     * <p>
     * 采用"先删后增"而非"逐条比对更新"的原因：实现简单，且工作经历数据量通常较小
     *
     * @param emp 员工信息（包含新的工作经历列表）
     */
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

    /**
     * 查询所有员工（不分页）
     *
     * @return 全部员工列表
     */
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
