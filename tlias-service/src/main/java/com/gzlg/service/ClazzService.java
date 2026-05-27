package com.gzlg.service;

import com.gzlg.common.PageResult;
import com.gzlg.pojo.Clazz;
import com.gzlg.pojo.ClazzQueryParam;

import java.util.List;

public interface ClazzService {

    PageResult page(ClazzQueryParam clazzQueryParam);

    void deleteById(Integer id);

    void save(Clazz clazz);

    Clazz getInfo(Integer id);

    void update(Clazz clazz);

    List<Clazz> listAll();
}
