package com.gzlg.mapper;

import com.gzlg.pojo.Clazz;
import com.gzlg.pojo.ClazzQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

@Mapper
public interface ClazzMapper {

    List<Clazz> list(ClazzQueryParam clazzQueryParam);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into clazz(name, room, begin_date, end_date, master_id, subject, create_time, update_time) " +
            "values (#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void insert(Clazz clazz);

    void deleteById(Integer id);

    Clazz selectById(Integer id);

    void updateById(Clazz clazz);

    List<Clazz> listAll();

    Integer countStudentByClazzId(Integer clazzId);
}
