package com.gtw.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.gtw.mybatis")
public class SpringMybatisApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringMybatisApplication.class, args);
    }

}
