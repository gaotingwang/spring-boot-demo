package com.gtw.websocket;

import com.gtw.websocket.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class WebsocketApplication {

    @Autowired
    private TestService testService;

    public static void main(String[] args) {
        SpringApplication.run(WebsocketApplication.class, args);
    }

    @GetMapping("/testSendMsg")
    public void sendMsg() {
        testService.sendMsg();
    }
}
