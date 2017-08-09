package com.gtw.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 根据规则形势的Rabbit配置
 * RabbitMQ 多做了一层抽象, 在发消息者和队列之间, 加入了交换器 (Exchange)。
 * 这样发消息者和队列就没有直接联系, 转而变成发消息者把消息给交换器, 交换器根据调度策略再把消息再给队列。
 */
@Configuration
public class TopicRabbitConfig {
    private final static String message = "topic.message";
    private final static String messages = "topic.messages";

    @Bean
    public Queue queueMessage() {
        return new Queue(TopicRabbitConfig.message);
    }

    @Bean
    public Queue queueMessages() {
        return new Queue(TopicRabbitConfig.messages);
    }

    @Bean
    TopicExchange exchange() {
        // 指定了交换机的名称
        return new TopicExchange("exchange");
    }

    /**
     * 交换机需要和消息队列进行绑定，指定queueMessage的routingKey规则为topic.message
     * @param queueMessage 消息队列
     * @param exchange 交换机
     * @return 绑定
     */
    @Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }

    /**
     * 交换机需要和消息队列进行绑定，指定queueMessages的routingKey规则为topic.#
     * @param queueMessages 消息队列
     * @param exchange 交换机
     * @return 绑定
     */
    @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
}
