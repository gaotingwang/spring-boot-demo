package com.gtw.mqtt.common;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class MqttConsumerCallBack implements MqttCallback {

    private final List<AbstractSubService> emqSubServices;

    public MqttConsumerCallBack(List<AbstractSubService> emqSubServices) {
        this.emqSubServices = emqSubServices;
    }

    /**
     * 客户端断开连接的回调
     */
    @Override
    public void connectionLost(Throwable throwable) {
        log.error("consumer connect error", throwable);
    }

    /**
     * 消息到达的回调
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String content = new String(message.getPayload());
        log.info("MQTT: 订阅主题:[{}],发来消息:[{}]", topic, content);
        try {
            ISubService targetSubService = emqSubServices.stream()
                    .filter(service -> service.supported(topic)).findFirst().orElse(null);
            if(targetSubService == null) {
                targetSubService = new DefaultSubService();
            }
            targetSubService.process(content);
        } catch (Exception e) {
            log.error("mqtt sub error", e);
        }
    }

    /**
     * 消息发布成功的回调
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("接收消息成功");
    }
}
