package com.gtw.event.listener;

import com.gtw.event.model.RegisterEvent;
import com.gtw.event.model.User;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

@Component
public class PresentRegisterListener implements RegisterListener,Ordered {

    public void onApplicationEvent(final RegisterEvent event) {
        execute();
        System.out.println("注册成功，赠送游戏大礼包给：" + ((User)event.getSource()).getUsername());
    }

    @Override
    public void execute() {
        System.out.println("PresentService: 调用PresentService发送礼物");
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
