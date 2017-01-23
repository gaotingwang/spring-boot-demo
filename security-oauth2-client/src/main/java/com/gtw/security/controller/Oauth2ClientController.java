package com.gtw.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Oauth2ClientController {
    @Autowired
    @Qualifier("gitHubRestTemplate")
    private OAuth2RestTemplate gitHubRestTemplate;
    @Autowired
    @Qualifier("myRestTemplate")
    private OAuth2RestTemplate myRestTemplate;

    @RequestMapping(value = "/testClient/gitHubLogin", method = RequestMethod.GET)
    public Object login1() {
        return gitHubRestTemplate.getForObject("https://api.github.com/user", Object.class);
    }

    @RequestMapping(value = "/testClient/myLogin", method = RequestMethod.GET)
    public Object login2() {
//        return myRestTemplate.getAccessToken();
        return myRestTemplate.getForObject("/testOauth/login", String.class);
    }
}
