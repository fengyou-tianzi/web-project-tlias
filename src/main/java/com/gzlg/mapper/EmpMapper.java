package com.gzlg.mapper;

import com.gzlg.pojo.Emp;
import com.gzlg.pojo.EmpQueryparam;
import org.apache.ibatis.annotations.Mapper;

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
}
