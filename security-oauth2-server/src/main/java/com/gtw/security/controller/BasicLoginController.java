package com.gtw.security.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicLoginController {

    @RequestMapping(value = "/api/login", method = RequestMethod.GET)
    public String login() {
        /**
         * 如果user为InMemoryAuthentication中的User,
         * (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()此处强转运行时会报错,
         * 同样@AuthenticationPrincipal注解也取不到值
         */
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
}
