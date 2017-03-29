package com.gtw.event.service;

import com.gtw.event.model.RegisterEvent;
import com.gtw.event.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PublishRegister {
    @Autowired
    private ApplicationEventPublisher publisher;
    /**
     * ③事件执行
     */
    public void publishRegisterEvent(User user) {
        publisher.publishEvent(new RegisterEvent(user));
    }
}
