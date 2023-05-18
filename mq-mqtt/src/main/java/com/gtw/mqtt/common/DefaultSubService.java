package com.gtw.mqtt.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultSubService implements ISubService {

    @Override
    public void process(String payload) {
        log.info("DefaultSubService receive msg is : {}", payload);
    }

}
