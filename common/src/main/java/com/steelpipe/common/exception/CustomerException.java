package com.steelpipe.common.exception;

import com.steelpipe.common.result.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerException extends RuntimeException {
    private String code;
    private String msg;

    public CustomerException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CustomerException(String msg) {
        this.code = String.valueOf(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        this.msg = msg;
    }
}
