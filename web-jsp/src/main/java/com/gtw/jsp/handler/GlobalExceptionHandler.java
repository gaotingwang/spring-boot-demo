package com.gtw.jsp.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 高廷旺
 * 创建时间: 2017-03-10 11:55
 * 创建原因: 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    public static final String DEFAULT_ERROR_VIEW = "error/500";

    @ExceptionHandler(value = RuntimeException.class)// 用来定义函数针对的异常类型
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        ModelAndView mav = new ModelAndView();

        mav.addObject("url", req.getRequestURL());
        mav.addObject("exception", e);
        mav.setViewName(DEFAULT_ERROR_VIEW);// 将Exception对象和请求URL映射到error.html中

        return mav;
    }
}
