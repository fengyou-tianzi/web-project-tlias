package com.gzlg.service;

import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpQueryparam;
import com.gzlg.pojo.LoginInfo;
import com.gzlg.pojo.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 员工管理接口
 */
public interface EmpService {

    /**
     * 条件分页查询
     */
    PageResult page(EmpQueryparam empQueryparam);

    /**
     * 添加员工
     */
    void save(Emp emp);

    /**
     * 根据ID删除员工（含经历信息）
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据ID查询员工基本信息和工作经历信息
     */
    Emp getInfo(Integer id);

    /**
     * 更新员工信息（基础信息+工作经历）
     */
    void update(Emp emp);

    /**
     * 查询全部员工
     */
    List<Emp> listAll();

    List<Map<String, Object>> getEmpGenderData();

    Map<String, Object> getEmpJobData();

    LoginInfo login(Emp emp);
}
