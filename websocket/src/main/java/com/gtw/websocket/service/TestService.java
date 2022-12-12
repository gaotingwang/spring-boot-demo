package com.gtw.websocket.service;

import com.alibaba.fastjson2.JSONObject;
import com.gtw.websocket.websocket.IWebSocketService;
import com.gtw.websocket.websocket.WsClientsManager;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class TestService {

    public int getCount() {
        Random random = new Random();
        return random.nextInt(10);
    }

    public void sendMsg() {
        IWebSocketService webSocketService = WsClientsManager.getWsServerByClientId("san.zhang");
        if(webSocketService == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "san.zhang");
        jsonObject.put("count", getCount());
        webSocketService.sendMessage(jsonObject.toJSONString());
    }
}
