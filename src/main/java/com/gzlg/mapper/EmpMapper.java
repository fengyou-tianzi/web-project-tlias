package com.gzlg.mapper;

import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpQueryparam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据ID删除员工基本信息
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据ID查询员工基本信息
     */
    Emp selectById(Integer id);

    /**
     * 根据ID更新员工基本信息
     */
    void updateById(Emp emp);

    /**
     * 查询全部员工
     */
    List<Emp> listAll();

    List<Map<String, Object>> countByGender();

    List<Map<String, Object>> countByJob();
}
