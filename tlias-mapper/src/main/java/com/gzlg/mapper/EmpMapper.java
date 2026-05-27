package com.gzlg.mapper;

import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpQueryparam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmpMapper {

    List<Emp> list(EmpQueryparam empQueryparam);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp selectById(Integer id);

    void updateById(Emp emp);

    List<Emp> listAll();

    List<Map<String, Object>> countByGender();

    List<Map<String, Object>> countByJob();

    Emp getByUsernameAndPassword(String username, String password);
}
