package com.gzlg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学员查询请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQueryParam {
    // 当前页
    private Integer page = 1;
    // 每页显示数量
    private Integer pageSize = 10;
    // 学员姓名
    private String name;
    // 最高学历
    private Integer degree;
    // 班级ID
    private Integer clazzId;
}
