package com.gtw.event.listener;

import com.gtw.event.model.RegisterEvent;
import org.springframework.context.ApplicationListener;

/**
 * ②对特定事件定义无序的监听器(统一事件的监听器过多，具体执行顺序是不保证的)
 *
 * 事件Listener的方式有三种：
 *      1.实现ApplicationListener（无序）
 *      2.实现SmartApplicationListener（有序）
 *      3.使用注解@EventListener
 */
public interface RegisterListener extends ApplicationListener<RegisterEvent> {

    void execute();
}
