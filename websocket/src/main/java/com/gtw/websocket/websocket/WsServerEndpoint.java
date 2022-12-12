package com.gtw.websocket.websocket;

import com.alibaba.fastjson2.JSONObject;
import com.gtw.websocket.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * 审批处理 websocket
 * @author Tingwang.Gao
 */
@Slf4j
@Component
@ServerEndpoint("/websocket/{username}")
public class WsServerEndpoint implements IWebSocketService {

    /**
     * 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Session session;

    private String username;

    private static TestService testService;

    /**
     * 注入bean的方式，因为spring管理的都是单例。WebSocket是对象，相冲突，所以采用对类属性赋值
     */
    @Autowired
    public void setTestService(TestService testService) {
        WsServerEndpoint.testService = testService;
    }

    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;
        WsClientsManager.registerClient(username, this);
        // todo 红点数量
        int count = testService.getCount();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("count", count);
        this.sendMessage(jsonObject.toJSONString());
        log.info("{}, 连接成功", username);
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        WsClientsManager.removeClient(username);
        log.info("{}, 连接关闭", username);
    }

    /**
     * 接收到消息
     */
    @OnMessage
    public void onMsg(String message) {
        log.info("msg:{}.", message);
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
