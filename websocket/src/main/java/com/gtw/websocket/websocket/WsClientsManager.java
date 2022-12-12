package com.gtw.websocket.websocket;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 审批管理 websocket 客户端管理
 * @author G
 */
public class WsClientsManager {

    /**
     * 用来存放每个客户端对应的 WebSocketServer 对象
     */
    private static ConcurrentHashMap<String, IWebSocketService> webSocketMap = new ConcurrentHashMap<>();

    public static void registerClient(String client, IWebSocketService webSocketService) {
        if (webSocketMap.containsKey(client)) {
            webSocketMap.remove(client);
            webSocketMap.put(client, webSocketService);
        } else {
            webSocketMap.put(client, webSocketService);
        }
    }

    public static void removeClient(String client) {
        if (webSocketMap.containsKey(client)) {
            webSocketMap.remove(client);
        }
    }

    public static IWebSocketService getWsServerByClientId(String client) {
        return webSocketMap.get(client);
    }
}
