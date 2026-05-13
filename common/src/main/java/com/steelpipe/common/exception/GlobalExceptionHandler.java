package com.steelpipe.common.exception;

import com.steelpipe.common.result.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        log.error("系统异常: {}", e.getMessage(), e);
        return R.error("系统异常，请稍后重试");
    }

    @ExceptionHandler(CustomerException.class)
    @ResponseBody
    public R customerError(CustomerException e) {
        log.warn("业务异常 [code={}]: {}", e.getCode(), e.getMsg());
        return R.error(Integer.valueOf(e.getCode()), e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public R validError(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse("参数错误");
        log.warn("参数校验失败: {}", msg);
        return R.error(20002, msg);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public R notFound(NoSuchElementException e) {
        log.warn("资源不存在: {}", e.getMessage());
        return R.error("数据不存在");
    }
}
