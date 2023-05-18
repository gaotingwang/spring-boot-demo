package com.gtw.mqtt.common;

import java.util.regex.Pattern;

public abstract class AbstractSubService implements ISubService {

    /**
     * 是否支持该 topic 对应内容的处理
     * @param topic topic
     * @return 是否支持
     */
    public boolean supported(String topic) {
        return Pattern.matches(this.supportTopicRegex(), topic);
    }

    public abstract String supportTopicRegex();

}
