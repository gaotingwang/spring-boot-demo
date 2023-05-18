package com.gtw.mqtt.common;

public interface ISubService {
    /**
     * 处理订阅到的消息
     * @param payload 消息内容
     */
    public abstract void process(String payload);
}
