package com.gtw.websocket.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisMessagePublisher {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void sendMessage(String channel, String message) {
        stringRedisTemplate.convertAndSend(channel, message);
    }
}
