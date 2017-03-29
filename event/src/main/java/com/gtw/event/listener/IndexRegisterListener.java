package com.gtw.event.listener;

import com.gtw.event.model.RegisterEvent;
import com.gtw.event.model.User;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class IndexRegisterListener {
    /**
     * ②可以直接使用@EventListener注解，不需要实现ApplicationListener
     */
    @EventListener
    @Order(1)
    public void processIndexEvent(final RegisterEvent event) {
        execute();
        System.out.println("注册成功，索引用户信息：" + ((User)event.getSource()).getUsername());
    }

    public void execute() {
        System.out.println("IndexService: 调用IndexService进行索引");
    }
}
