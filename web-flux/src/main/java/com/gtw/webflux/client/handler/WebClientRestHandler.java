package com.gtw.webflux.client.handler;

import com.gtw.webflux.client.handler.bean.MethodInfo;
import com.gtw.webflux.client.handler.bean.ServerInfo;

public class WebClientRestHandler implements RestHandler {

    /**
     * 初始化WebClient
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {
    }

    @Override
    public Object invoke(MethodInfo methodInfo) {
        return null;
    }
}
