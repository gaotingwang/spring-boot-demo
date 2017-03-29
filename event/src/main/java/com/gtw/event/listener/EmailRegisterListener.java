package com.gtw.event.listener;

import com.gtw.event.model.RegisterEvent;
import com.gtw.event.model.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * ②SmartApplicationListener可以定义有序监听器
 */
@Component
public class EmailRegisterListener implements SmartApplicationListener {
    /**
     * 用于指定支持的事件类型，只有支持的才调用onApplicationEvent
     */
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == RegisterEvent.class;
    }

    /**
     * 支持的目标类型，只有支持的才调用onApplicationEvent
     */
    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == User.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        execute();
        System.out.println("注册成功，发送确认邮件给：" + ((User)event.getSource()).getUsername());
    }

    /**
     * 顺序，越小优先级越高
     */
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    public void execute() {
        System.out.println("EmailService: 调用EmailService发送邮件");
    }
}
