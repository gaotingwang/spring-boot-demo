package com.gtw.event.model;

import org.springframework.context.ApplicationEvent;

/**
 * ①定义需要监听的事件
 */
public class RegisterEvent extends ApplicationEvent {

    public RegisterEvent(User user) {
        super(user);
    }
}
