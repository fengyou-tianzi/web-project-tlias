package com.gzlg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gzlg.common.PageResult;
import com.gzlg.exception.BusinessException;
import com.gzlg.mapper.ClazzMapper;
import com.gzlg.pojo.Clazz;
import com.gzlg.pojo.ClazzQueryParam;
import com.gzlg.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;

    private String calculateStatus(LocalDate beginDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (now.isAfter(endDate)) {
            return "已结课";
        } else if (now.isBefore(beginDate)) {
            return "未开班";
        } else {
            return "在读中";
        }
    }

    @Override
    public PageResult page(ClazzQueryParam clazzQueryParam) {
        PageHelper.startPage(clazzQueryParam.getPage(), clazzQueryParam.getPageSize());
        Page<Clazz> page = (Page<Clazz>) clazzMapper.list(clazzQueryParam);
        page.getResult().forEach(clazz -> {
            clazz.setStatus(calculateStatus(clazz.getBeginDate(), clazz.getEndDate()));
        });
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        Integer studentCount = clazzMapper.countStudentByClazzId(id);
        if (studentCount != null && studentCount > 0) {
            throw new BusinessException("对不起, 该班级下有学生, 不能直接删除");
        }
        clazzMapper.deleteById(id);
    }

    @Override
    public void save(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.insert(clazz);
    }

    @Override
    public Clazz getInfo(Integer id) {
        return clazzMapper.selectById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);
    }

    @Override
    public List<Clazz> listAll() {
        return clazzMapper.listAll();
    }
}
