package com.gzlg.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * 编码统一响应
 */
@Getter
@Setter
public class Result {
    //成功：1 失败：0
    private Integer code;
    //错误信息
    private String msg;
    //数据
    private Object data;

    public static Result success() {
        Result result = new Result();
        result.code=1;
        result.msg="success";
        return result;
    }

    public static Result success(Object data) {
        Result result = success();
        result.data = data;
        return result;
    }

    public static Result error(String msg) {
        Result result = new Result();
        result.code=0;
        result.msg=msg;
        return result;
    }
}
