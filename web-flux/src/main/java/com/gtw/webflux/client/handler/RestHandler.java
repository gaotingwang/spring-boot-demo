package com.gtw.webflux.client.handler;

import com.gtw.webflux.client.handler.bean.MethodInfo;
import com.gtw.webflux.client.handler.bean.ServerInfo;

public interface RestHandler {
    /**
     * 初始化服务器信息
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);

    /**
     * 调用rest请求返回结果
     * @param methodInfo
     * @return
     */
    Object invoke(MethodInfo methodInfo);
}
