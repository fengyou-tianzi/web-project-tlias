package com.gzlg.mapper;

import com.gzlg.pojo.Clazz;
import com.gzlg.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * 班级Mapper
 */
@Mapper
public interface ClazzMapper {

    /**
     * 条件分页查询班级列表（关联班主任姓名）
     */
    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    /**
     * 添加班级
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time) " +
            "values (#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void insert(Clazz clazz);

    /**
     * 根据ID删除班级
     */
    void deleteById(Integer id);

    /**
     * 根据ID查询班级
     */
    Clazz selectById(Integer id);

    /**
     * 根据ID更新班级
     */
    void updateById(Clazz clazz);

    /**
     * 查询所有班级
     */
    List<Clazz> listAll();

    /**
     * 根据班级ID查询关联的学生数量
     */
    Integer countStudentByClazzId(Integer clazzId);
}
