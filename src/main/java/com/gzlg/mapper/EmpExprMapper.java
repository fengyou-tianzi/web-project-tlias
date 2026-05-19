package com.gzlg.mapper;

import com.gzlg.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 员工经历Mapper
 */
@Mapper
public interface EmpExprMapper {

    /**
     * 批量添加员工经历信息
     */
    void insertBatch(List<EmpExpr> exprList);
}
