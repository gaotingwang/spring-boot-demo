package com.gtw.mqtt.service;

import com.gtw.mqtt.common.AbstractSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestSubService extends AbstractSubService {

    @Override
    public String supportTopicRegex() {
        return "^aaatopic/.+";
    }

    @Override
    public void process(String payload) {
        log.info("TestSubService receive msg is : {}", payload);
    }

}
