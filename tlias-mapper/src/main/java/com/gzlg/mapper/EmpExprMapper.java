package com.gzlg.mapper;

import com.gzlg.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {

    void insertBatch(List<EmpExpr> exprList);

    void deleteByEmpIds(List<Integer> empId);

    List<EmpExpr> selectByEmpId(Integer empId);
}
