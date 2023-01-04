package com.gtw.websocket.event.listener;

import com.alibaba.fastjson2.JSONObject;
import com.gtw.websocket.common.RedisTopicConstant;
import com.gtw.websocket.websocket.IWebSocketService;
import com.gtw.websocket.websocket.WsClientsManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        String topic = new String(message.getChannel());
        String msg = new String(message.getBody());
        if (RedisTopicConstant.APPROVAL_COUNT_TOPIC.equals(topic)) {
            IWebSocketService webSocketService = WsClientsManager.getWsServerByClientId(msg);
            if (webSocketService != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", msg);
                jsonObject.put("count", getCount(msg));
                webSocketService.sendMessage(jsonObject.toJSONString());
            }
        }
    }

    private int getCount(String msg) {
        return 10;
    }
}
