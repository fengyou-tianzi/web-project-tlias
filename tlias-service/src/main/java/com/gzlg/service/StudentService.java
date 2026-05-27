package com.gzlg.service;

import com.gzlg.common.PageResult;
import com.gzlg.pojo.Student;
import com.gzlg.pojo.StudentQueryParam;

import java.util.List;
import java.util.Map;

public interface StudentService {

    PageResult page(StudentQueryParam studentQueryParam);

    void deleteByIds(String ids);

    void save(Student student);

    Student getInfo(Integer id);

    void update(Student student);

    void violation(Integer id, Integer score);

    List<Map<String, Object>> getStudentDegreeData();

    Map<String, Object> getStudentCountData();
}
