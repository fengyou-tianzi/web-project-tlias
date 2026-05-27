package com.gzlg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzlg.common.PageResult;
import com.gzlg.mapper.StudentMapper;
import com.gzlg.pojo.Student;
import com.gzlg.pojo.StudentQueryParam;
import com.gzlg.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult page(StudentQueryParam studentQueryParam) {
        PageHelper.startPage(studentQueryParam.getPage(), studentQueryParam.getPageSize());
        Page<Student> page = (Page<Student>) studentMapper.list(studentQueryParam);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteByIds(String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        studentMapper.deleteByIds(idList);
    }

    @Override
    public void save(Student student) {
        student.setViolationCount(0);
        student.setViolationScore(0);
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.insert(student);
    }

    @Override
    public Student getInfo(Integer id) {
        return studentMapper.selectById(id);
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateById(student);
    }

    @Override
    public void violation(Integer id, Integer score) {
        studentMapper.updateViolation(id, score);
    }

    @Override
    public List<Map<String, Object>> getStudentDegreeData() {
        log.debug("开始统计学员学历分布");
        List<Map<String, Object>> degreeList = studentMapper.countByDegree();
        log.debug("学员学历统计完成, 共{}种学历", degreeList.size());
        return degreeList;
    }

    @Override
    public Map<String, Object> getStudentCountData() {
        log.debug("开始统计每个班级的人数");
        List<Map<String, Object>> clazzList = studentMapper.countByClazz();
        Map<String, Object> result = new HashMap<>();
        result.put("clazzList", clazzList.stream().map(m -> m.get("name")).collect(Collectors.toList()));
        result.put("dataList", clazzList.stream().map(m -> m.get("value")).collect(Collectors.toList()));
        log.debug("班级人数统计完成, 共{}个班级", clazzList.size());
        return result;
    }
}
