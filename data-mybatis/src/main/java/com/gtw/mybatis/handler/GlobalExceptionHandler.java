package com.gtw.mybatis.handler;

import com.gtw.mybatis.domain.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 高廷旺
 * 创建时间: 2017-03-10 11:55
 * 创建原因: 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest request, RuntimeException e) throws Exception {
        return new ErrorInfo<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), request.getRequestURL().toString(), "Some Data");
    }
}
