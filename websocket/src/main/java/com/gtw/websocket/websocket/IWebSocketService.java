package com.gtw.websocket.websocket;

/**
 * websocket 统一接口
 */
public interface IWebSocketService {

    /**
     * 向客户端发送消息
     * @param msg 消息内容
     */
    void sendMessage(String msg);
}
