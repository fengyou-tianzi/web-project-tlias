package com.gzlg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult {
    //总记录数
    private Long total;
    //结果数据
    private List<?> rows;
}
