package com.gzlg.service;

import com.gzlg.common.PageResult;
import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpQueryparam;
import com.gzlg.pojo.LoginInfo;

import java.util.List;
import java.util.Map;

public interface EmpService {

    PageResult page(EmpQueryparam empQueryparam);

    void save(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp getInfo(Integer id);

    void update(Emp emp);

    List<Emp> listAll();

    List<Map<String, Object>> getEmpGenderData();

    Map<String, Object> getEmpJobData();

    LoginInfo login(Emp emp);
}
