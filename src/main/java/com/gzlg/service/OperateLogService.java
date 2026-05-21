package com.gzlg.service;

import com.gzlg.pojo.PageResult;

/**
 * 操作日志管理接口
 */
public interface OperateLogService {

    /**
     * 分页查询操作日志
     */
    PageResult page(Integer page, Integer pageSize);
}
