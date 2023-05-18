package com.gtw.mqtt.config;

import com.gtw.mqtt.exception.MqException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MqttProperties.class)
@Slf4j
public class MqttAutoConfiguration {

    /**
     * 发布者客户端和服务端建立连接
     */
    @Bean
    public MqttClient publishClient(MqttProperties mqttProperties) {
        MqttClient publishClient;
        try {
            //先让客户端和服务器建立连接，MemoryPersistence设置clientId的保存形式，默认为以内存保存
            publishClient = new MqttClient(mqttProperties.getHost(), mqttProperties.getPubClientId(), new MemoryPersistence());
            //发布消息不需要回调连接
            //client.setCallback(new PushCallback());

            MqttConnectOptions options = this.getOptions(mqttProperties);
            //判断拦截状态，这里注意一下，如果没有这个判断，是非常坑的
            if (!publishClient.isConnected()) {
                publishClient.connect(options);
            }else {//这里的逻辑是如果连接成功就重新连接
                publishClient.disconnect();
                publishClient.connect(this.getOptions(options));
            }
        } catch (MqttException e) {
            log.error("mqtt 连接失败", e);
            throw new MqException(e);
        }
        return publishClient;
    }

    /**
     * 订阅端的链接方法，关键是回调类的设置，要对订阅的主题消息进行处理
     * 断线重连方法，如果是持久订阅，重连时不需要再次订阅
     * 如果是非持久订阅，重连是需要重新订阅主题 取决于options.setCleanSession(true);
     * true为非持久订阅
     */
    @Bean
    public MqttClient subscribeClient(MqttProperties mqttProperties, MqttCallback mqttConsumerCallBack) {
        MqttClient subscribeClient;
        try {
            //clientId不能和其它的clientId一样，否则会出现频繁断开连接和重连的问题
            subscribeClient = new MqttClient(mqttProperties.getHost(), mqttProperties.getSubClientId(), new MemoryPersistence());// MemoryPersistence设置clientid的保存形式，默认为以内存保存
            //如果是订阅者则添加回调类，发布不需要
            subscribeClient.setCallback(mqttConsumerCallBack);

            MqttConnectOptions options = this.getOptions(mqttProperties);
            //判断拦截状态，这里注意一下，如果没有这个判断，是非常坑的
            if (!subscribeClient.isConnected()) {
                subscribeClient.connect(options);
            }else {//这里的逻辑是如果连接成功就重新连接
                subscribeClient.disconnect();
                subscribeClient.connect(this.getOptions(options));
            }

            //订阅主题
            subscribeClient.subscribe(mqttProperties.getTopics());
        } catch (MqttException e) {
            log.error("mqtt 连接失败", e);
            throw new MqException(e);
        }
        return subscribeClient;
    }

    private MqttConnectOptions getOptions(MqttProperties mqttProperties) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(mqttProperties.isCleanSession());
        options.setUserName(mqttProperties.getUsername());
        options.setPassword(mqttProperties.getPassword().toCharArray());
        options.setConnectionTimeout(mqttProperties.getConnectionTimeout());
        //设置心跳
        options.setKeepAliveInterval(mqttProperties.getKeepalive());
        // 设置自动重连, 其它具体参数可以查看MqttConnectOptions
        options.setAutomaticReconnect(true);
        return options;
    }

    private MqttConnectOptions getOptions(MqttConnectOptions options) {
        options.setCleanSession(options.isCleanSession());
        options.setUserName(options.getUserName());
        options.setPassword(options.getPassword());
        options.setConnectionTimeout(options.getConnectionTimeout());
        options.setKeepAliveInterval(options.getKeepAliveInterval());
        // 设置自动重连, 其它具体参数可以查看MqttConnectOptions
        options.setAutomaticReconnect(true);
        return options;
    }
}
