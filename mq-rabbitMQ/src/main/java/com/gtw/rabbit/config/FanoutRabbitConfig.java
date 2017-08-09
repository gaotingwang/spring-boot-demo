package com.gtw.rabbit.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Fanout 就是广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息
 */
@Configuration
public class FanoutRabbitConfig {

    @Bean
    public Queue aMessageQueue() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue bMessageQueue() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue cMessageQueue() {
        return new Queue("fanout.C");
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    /**
     * 以下是给交换器绑定指定的消息队列，这样给交换器发送的消息，它绑定的队列都可以收到
     */
    @Bean
    Binding bindingExchangeA(Queue aMessageQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(aMessageQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue bMessageQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(bMessageQueue).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue cMessageQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(cMessageQueue).to(fanoutExchange);
    }
}
