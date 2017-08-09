package com.gtw.rabbit.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 点对点消息发送者
 */
@Component
public class HelloSender {
    private final AmqpTemplate rabbitTemplate;

    @Autowired
    public HelloSender(AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 采用Direct Exchange方式，根据routing_key去发布获取消息
     */
    public void directSend() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("hello", context);
    }

    /**
     * 采用Topic Exchange,指定了交换机名称和routing_key规则
     * send1()会匹配到topic.#和topic.message 两个Receiver都可以收到消息
     */
    public void topicSend1() {
        String context = "hi, i am message 1";
        System.out.println("Sender : " + context);

        this.rabbitTemplate.convertAndSend("exchange", "topic.message", context);
    }

    /**
     * send2()只有topic.#可以匹配所有只有Receiver2监听到消息
     */
    public void topicSend2() {
        String context = "hi, i am messages 2";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages", context);
    }

    /**
     * 在FanoutRabbitConfig里使用了A、B、C三个队列绑定到Fanout交换机上面，发送端的routing_key写任何字符都会被忽略
     */
    public void fanoutSend() {
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
    }
}
