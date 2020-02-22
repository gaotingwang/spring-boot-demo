package com.gtw.spring.boot.sample;

import com.gtw.spring.boot.autoconfigure.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SampleApplication {
    @Autowired
    private GreetingService greetingService;

    @PostConstruct
    public void sayHello(){
        greetingService.sayHello();
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleApplication.class, args);
    }
}
