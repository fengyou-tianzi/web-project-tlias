package com.gzlg.mapper;

import com.gzlg.pojo.OperateLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 操作日志Mapper
 */
@Mapper
public interface OperateLogMapper {

    /**
     * 分页查询操作日志（关联操作人姓名）
     */
    List<OperateLog> list();
}
