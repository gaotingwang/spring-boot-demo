package com.gtw.webflux.client.controller;

import com.gtw.webflux.client.IUserApi;
import com.gtw.webflux.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/userApi")
public class UserApiController {
    @Autowired
    private IUserApi userApi;

    @GetMapping
    public void test() {
        userApi.createUser(Mono.just(User.builder().name("test").age(18).build()))
                .subscribe(System.out::println, e -> System.err.println("操作异常：" + e.getMessage()));

        Flux<User> users = userApi.getAllUser();
        users.subscribe(System.out::println, e -> System.err.println("操作异常：" + e.getMessage()));

        userApi.deleteUser("123").subscribe(u -> {}, e -> {
            System.err.println("操作异常：" + e.getMessage());
        });
    }

}
