package com.gtw.mybatis.controller;

import com.gtw.mybatis.domain.*;
import com.gtw.mybatis.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@RestController
public class Controller {
    @Autowired
    private ITransRepository transRepository;

    @RequestMapping(value = "/test/query", method = RequestMethod.GET)
    public Trans testQuery(String key){
        if(StringUtils.isEmpty(key)){
            key = "local/audio/group6/a";
        }else {
            key = "local/audio/group6/c.mp4";
        }
        return transRepository.query(key);
    }

    @RequestMapping(value = "/test/add", method = RequestMethod.POST)
    public void testAdd(){
        Audio audio = new Audio("local/yy.mp3", Audio.Quality.MP3_160);
        Trans trans = new AudioTrans(Collections.singletonList(audio), "local/yy.mp3");
        transRepository.add(trans);
    }

    @RequestMapping("/globalError")
    public String jsonError() {
        throw new RuntimeException("发生运行时异常");
    }


    @RequestMapping("/selfError")
    public String selfError() {
        throw new MyException("发生自定义异常");
    }

    /**
     * 处理自己内部的异常
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo<String> jsonErrorHandler(HttpServletRequest request, HttpServletResponse response, MyException e) throws Exception {
        return new ErrorInfo<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), request.getRequestURL().toString(), "Some Data");
    }
}
