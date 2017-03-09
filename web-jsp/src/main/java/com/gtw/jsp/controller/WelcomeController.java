package com.gtw.jsp.controller;

import com.gtw.jsp.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WelcomeController {

    private String message = "Hello World";

    @GetMapping("/")
    public String welcome(Map<String, Object> model) {
        User user = new User(1, "张三", "男", 33);
        model.put("createTime", new Date());
        model.put("user", user);
        return "welcome";
    }

    @RequestMapping("/foo")
    @ResponseBody
    public Map<String, Object> foo() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Ryan");
        map.put("age", "3");
        map.put("sex", "man");
        return map;
    }

    @RequestMapping("/favicon.ico")
    @ResponseBody
    void favicon() {}

}
