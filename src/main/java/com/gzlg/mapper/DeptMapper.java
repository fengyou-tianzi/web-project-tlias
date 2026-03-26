package com.gzlg.mapper;

import com.gzlg.pojo.Dept;
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
}
