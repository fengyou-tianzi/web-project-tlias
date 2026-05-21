package com.gzlg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 班级类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clazz {
    // ID, 主键
    private Integer id;
    // 班级名称
    private String name;
    // 班级教室
    private String room;
    // 开课时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;
    // 结课时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    // 班主任ID, 关联员工表ID
    private Integer masterId;
    // 学科, 1:java, 2:前端, 3:大数据, 4:Python, 5:Go, 6:嵌入式
    private Integer subject;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;

    // 班主任姓名（关联查询emp表）
    private String masterName;
    // 班级状态（根据时间动态计算）
    private String status;
}
