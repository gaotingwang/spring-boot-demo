package com.gtw.mqtt.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmqSubTopicEnum {
    TEST_TOPIC("aaatopic/#");

    private final String topicName;

    public static EmqSubTopicEnum topicOf(String topicName) {
        for (EmqSubTopicEnum subTypeEnum : EmqSubTopicEnum.values()) {
            if (topicName.contains(subTypeEnum.getTopicName())) {
                return subTypeEnum;
            }
        }
        throw new IllegalArgumentException("Unknown topicName : " + topicName);
    }
}
