package com.aiassistance.Result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T>{
    int code;
    String message;
    Object data;
    // 成功方法 - 无参
    public static <T> Result<T> success(String message) {
        return new Result(200, message,null);
    }

    // 成功方法 - 带数据
    public static<T> Result<T> success(String message,T data) {
        return new Result(200, message, data);
    }

    // 失败方法
    public static<T> Result<T> error(Integer code, String message) {
        return new Result(code, message,null);
    }
}
