package com.gzlg.service;

import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpQueryparam;
import com.gzlg.pojo.PageResult;

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
}
