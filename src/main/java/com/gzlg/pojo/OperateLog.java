package com.gzlg.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 操作日志类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperateLog {
    // ID, 主键
    private Integer id;
    // 操作人ID
    private Integer operateEmpId;
    // 操作时间
    private LocalDateTime operateTime;
    // 操作类名
    private String className;
    // 操作方法名
    private String methodName;
    // 方法参数
    private String methodParams;
    // 返回值
    private String returnValue;
    // 耗时(毫秒)
    private Integer costTime;

    // 操作人姓名（关联查询emp表）
    private String operateEmpName;
}
