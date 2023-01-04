package com.gtw.websocket.service;

import com.gtw.websocket.common.RedisTopicConstant;
import com.gtw.websocket.event.RedisMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TestService {

    @Autowired
    private RedisMessagePublisher publisher;

    public int getCount() {
        Random random = new Random();
        return random.nextInt(10);
    }

    public void sendMsg(String name) {
//        IWebSocketService webSocketService = WsClientsManager.getWsServerByClientId(name);
//        if(webSocketService == null) {
//            return;
//        }
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("username", name);
//        jsonObject.put("count", getCount());
//        webSocketService.sendMessage(jsonObject.toJSONString());

        publisher.sendMessage(RedisTopicConstant.APPROVAL_COUNT_TOPIC, name);
    }
}
