package com.gzlg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 班级查询请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClazzQueryParam {
    // 当前页
    private Integer page = 1;
    // 每页显示数量
    private Integer pageSize = 10;
    // 班级名称
    private String name;
    // 开课开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;
    // 开课结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}
