package com.gtw.event.listener;

import com.gtw.event.model.RegisterEvent;
import com.gtw.event.model.User;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PointRegisterListener implements RegisterListener {

    /**
     * 由于 Spring 的事件处理是单线程的，所以如果一个事件被发布，
     * 直至并且除非所有的接收者得到的该消息，该进程被阻塞并且流程将不会继续。
     * 事件需要异步执行加@Async
     */
    @Async
    @Override
    public void onApplicationEvent(RegisterEvent event) {
        throw new RuntimeException("运行异常");
//        execute();
//        System.out.println("注册成功，赠送积分给：" + ((User)event.getSource()).getUsername());
    }

    @Override
    public void execute() {
        System.out.println("PointService: 调用PointService计算积分");
    }
}
