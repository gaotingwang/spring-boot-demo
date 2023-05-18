package com.gtw.mqtt.controller;

import com.gtw.mqtt.common.MqttServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttTestController {

    @Autowired
    private MqttServer mqttServer;

    @GetMapping("/testSend")
    public boolean testMqttSend(@RequestParam String content) {
        return mqttServer.sendMQTTMessage("aaatopic/test", content, 0);
    }
}
