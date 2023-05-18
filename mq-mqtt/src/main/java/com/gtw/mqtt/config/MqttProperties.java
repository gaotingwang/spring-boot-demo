package com.gtw.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "mqtt")
public class MqttProperties {
    /**
     * MQTT-服务端地址
     */
    private String host;
    /**
     * MQTT-服务端用户名
     */
    private String username;
    /**
     * MQTT-服务端密码
     */
    private String password;
    /**
     * MQTT-是否清理session
     *  设置false表示服务器会保留客户端的连接记录（订阅主题，qos）,客户端重连之后能获取到服务器在客户端断开连接期间推送的消息
     *  设置true表示每次连接服务器都是以新的身份
     */
    private boolean cleanSession;
    /**
     * MQTT-当前客户端的唯一标识
     */
    private String pubClientId;
    private String subClientId;
    /**
     * 发送超时时间
     */
    private int timeout;
    /**
     * 心跳时间
     */
    private int keepalive;
    /**
     * 连接超时时间
     */
    private int connectionTimeout;
    /**
     * 需要订阅的主题
     */
    private String[] topics;
}
