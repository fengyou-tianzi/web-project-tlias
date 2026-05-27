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

    /**
     * 按性别统计员工人数
     *
     * @return 性别统计列表，每项 Map 包含 name（性别标签）和 value（人数）
     */
    List<Map<String, Object>> countByGender();

    /**
     * 按职位统计员工人数
     *
     * @return 职位统计列表，每项 Map 包含 name（职位名称）和 value（人数）
     */
    List<Map<String, Object>> countByJob();

    Emp getByUsernameAndPassword(String username, String password);
}
