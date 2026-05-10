package com.gzlg.mapper;

import com.gzlg.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {

    /**
     * 查询所有部门
     */
    @Select("SELECT id,name,create_time,update_time FROM dept ORDER BY update_time DESC")
    List<Dept> findAll();

    /**
     * 根据ID删除部门
     */
    @Delete("DELETE FROM dept WHERE id = #{id}")
    void deleteById(Integer id);

    /**
     * 新增部门
     */
    @Insert("INSERT INTO dept(name, create_time, update_time) VALUES(#{name}, NOW(), NOW())")
    void add(Dept dept);
}
