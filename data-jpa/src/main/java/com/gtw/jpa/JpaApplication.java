package com.gtw.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.Instant;

@SpringBootApplication
@EnableJpaAuditing// 开启审计功能
public class JpaApplication {

    public static void main(String[] args) {
        System.out.println(Instant.now().getEpochSecond());
        SpringApplication.run(JpaApplication.class, args);
    }
}
