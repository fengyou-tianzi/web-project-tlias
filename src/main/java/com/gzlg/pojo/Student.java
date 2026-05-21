package com.gzlg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 学员类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    // ID, 主键
    private Integer id;
    // 学员姓名
    private String name;
    // 学号
    private String no;
    // 性别, 1:男, 2:女
    private Integer gender;
    // 手机号
    private String phone;
    // 最高学历, 1:初中, 2:高中, 3:大专, 4:本科, 5:硕士, 6:博士
    private Integer degree;
    // 身份证号
    private String idCard;
    // 是否大学, 0:否, 1:是
    private Integer isCollege;
    // 联系地址
    private String address;
    // 毕业日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate graduationDate;
    // 违纪次数
    private Integer violationCount;
    // 违纪扣分
    private Integer violationScore;
    // 班级ID, 关联班级表ID
    private Integer clazzId;
    // 创建时间
    private LocalDateTime createTime;
    // 修改时间
    private LocalDateTime updateTime;

    // 班级名称（关联查询clazz表）
    private String clazzName;
}
