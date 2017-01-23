package com.gtw.mybatis.controller;

import com.gtw.mybatis.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
