package com.gtw.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Oauth2ServerController {

    /**
     * 1.用curl -u web: http://localhost:8088/oauth/token -d "username=admin&password=password&scope=read&grant_type=password"获取token
     * 2.请求Headers中添加Authorization: Bearer token
     */
    @RequestMapping(value = "/testOauth/login", method = RequestMethod.GET)
    public String login() {
        /**
         * 如果是client_credentials授权模式此处直接获取到的是clientId不是UserDetails
         */
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
}
