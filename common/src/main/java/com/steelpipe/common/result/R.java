package com.steelpipe.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装返回结果的类，使用 Lombok 注解减少 boilerplate 代码
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R {
    private Boolean success;
    private Integer code;
    private String message;
    private Object data;
    private Map<String, Object> dataMap = new HashMap<>();

    private R() {}

    public static R ok() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    public static R success(Object data) {
        R result = new R();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setData(data);
        result.setMessage("请求成功");
        return result;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return r;
    }

    public static R error(Integer code, String msg) {
        R result = new R();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public static R error(String msg) {
        R result = new R();
        result.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        result.setMessage(msg);
        return result;
    }

    public static R setResult(ResultCodeEnum result) {
        R r = new R();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }

    /** 链式编程 */
    public R data(Map<String, Object> map) {
        this.setData(null);
        this.setDataMap(map);
        return this;
    }

    public R data(String key, Object value) {
        this.setData(null);
        this.dataMap.put(key, value);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}
