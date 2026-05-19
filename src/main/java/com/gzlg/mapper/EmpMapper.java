package com.gzlg.mapper;

import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpQueryparam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;

/**
 * 员工Mapper
 */
@Mapper
public interface EmpMapper {
    /**
     * 条件分页查询所有员工
     */
    List<Emp> list(EmpQueryparam empQueryparam);

    /**
     * 添加员工基本信息
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) " +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);
}
