package com.gtw.mqtt.common;

import com.gtw.mqtt.exception.MqttSendException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MqttServer {
    private final MqttClient publishClient;

    public MqttServer(MqttClient publishClient) {
        this.publishClient = publishClient;
    }

    /**
     * MQTT发送指令：主要是组装消息体
     * @param topic 主题
     * @param data 消息内容
     * @param qos 消息级别
     *            0：消息最多传递一次，不再关心它有没有发送到对方，也不设置任何重发机制
     *            1：包含了简单的重发机制，发送消息之后等待接收者的回复，如果没收到回复则重新发送消息。这种模式能保证消息至少能到达一次，但无法保证消息重复
     *            2：有了重发和重复消息发现机制，保证消息到达对方并且严格只到达一次
     */
    public boolean sendMQTTMessage(String topic, String data, int qos) {
        try {
            MqttTopic mqttTopic = this.publishClient.getTopic(topic);
            MqttMessage message = new MqttMessage();
            message.setQos(qos);
            //如果重复消费，则把值改为true,然后发送一条空的消息，之前的消息就会覆盖，然后在改为false
            message.setRetained(false);
            message.setPayload(data.getBytes());

            //将组装好的消息发出去
            return publish(mqttTopic, message);
        } catch (Exception e) {
            throw new MqttSendException(e);
        }
    }

    private boolean publish(MqttTopic topic , MqttMessage message) {
        MqttDeliveryToken token;
        try {
            //把消息发送给对应的主题
            token = topic.publish(message);
            token.waitForCompletion();
            //检查发送是否成功
            boolean flag = token.isComplete();

            StringBuilder sbf = new StringBuilder(200);
            sbf.append("给主题为'").append(topic.getName());
            sbf.append("'发布消息：");
            if (flag) {
                sbf.append("成功！消息内容是：").append(new String(message.getPayload()));
            } else {
                sbf.append("失败！");
            }
            log.info(sbf.toString());
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        return token.isComplete();
    }
}
