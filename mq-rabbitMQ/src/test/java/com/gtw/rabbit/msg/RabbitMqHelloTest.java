package com.gtw.rabbit.msg;

import com.gtw.rabbit.sender.HelloSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqHelloTest {

    @Autowired
    private HelloSender helloSender;

    @Test
    public void directSend() throws Exception {
        helloSender.directSend();
    }

    @Test
    public void topicSend1() throws Exception {
        helloSender.topicSend1();
    }

    @Test
    public void topicSend2() throws Exception {
        helloSender.topicSend2();
    }

    @Test
    public void fanoutSend() throws Exception {
        helloSender.fanoutSend();
    }
}
