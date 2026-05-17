package com.gzlg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * 员工查询请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpQueryparam {
    //当前页
    private Integer page=1;
    //每页显示数量
    private Integer pageSize=10;
    //员工姓名
    private String name;
    //员工性别
    private Integer gender;
    //入职开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate begin;
    //入职结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate end;
}
