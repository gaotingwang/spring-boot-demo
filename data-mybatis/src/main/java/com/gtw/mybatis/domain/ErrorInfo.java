package com.gtw.mybatis.domain;

import lombok.Value;
/**
 * @author 高廷旺
 * 创建时间: 2017-03-10 15:11
 * 创建原因: 全局异常信息
 */
@Value
public class ErrorInfo<T> {
    /**
     * 错误状态码
     */
    private Integer code;
    private String message;
    private String url;
    private T data;
}