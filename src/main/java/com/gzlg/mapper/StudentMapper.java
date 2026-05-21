package com.gzlg.mapper;

import com.gzlg.pojo.Student;
import com.gzlg.pojo.StudentQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 学员Mapper
 */
@Mapper
public interface StudentMapper {

    /**
     * 条件分页查询学员列表（关联班级名称）
     */
    List<Student> list(StudentQueryParam studentQueryParam);

    /**
     * 批量删除学员
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 添加学员
     */
    void insert(Student student);

    /**
     * 根据ID查询学员
     */
    Student selectById(Integer id);

    /**
     * 根据ID更新学员
     */
    void updateById(Student student);

    /**
     * 违纪处理（增加违纪次数和扣分）
     */
    void updateViolation(Integer id, Integer score);
}
