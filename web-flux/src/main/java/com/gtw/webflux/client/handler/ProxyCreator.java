package com.gtw.webflux.client.handler;

/**
 * 创建代理类接口
 */
public interface ProxyCreator {

    /**
     * 创建代理类
     */
    Object createProxy(Class<?> type);
}
