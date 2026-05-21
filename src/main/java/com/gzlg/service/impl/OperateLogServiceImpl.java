package com.gzlg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzlg.mapper.OperateLogMapper;
import com.gzlg.pojo.OperateLog;
import com.gzlg.pojo.PageResult;
import com.gzlg.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志 Service 实现类
 */
@Slf4j
@Service
public class OperateLogServiceImpl implements OperateLogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageResult page(Integer page, Integer pageSize) {
        log.debug("开始分页查询操作日志, page: {}, pageSize: {}", page, pageSize);
        PageHelper.startPage(page, pageSize);
        Page<OperateLog> result = (Page<OperateLog>) operateLogMapper.list();
        log.debug("操作日志查询完成, 总记录数: {}, 当前页记录数: {}", result.getTotal(), result.size());
        return new PageResult(result.getTotal(), result.getResult());
    }
}
