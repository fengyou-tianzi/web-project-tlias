package com.gzlg.mapper;

import com.gzlg.pojo.Student;
import com.gzlg.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StudentMapper {

    List<Student> list(StudentQueryParam studentQueryParam);

    void deleteByIds(List<Integer> ids);

    void insert(Student student);

    Student selectById(Integer id);

    void updateById(Student student);

    void updateViolation(Integer id, Integer score);

    List<Map<String, Object>> countByDegree();

    List<Map<String, Object>> countByClazz();
}
