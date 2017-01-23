package com.gtw.jackson.model.deserialization;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class JsonAnySetterBean {
    public String name;
    public Map<String, String> properties = new HashMap<>();//需要对属性进行初始化，在构造函数中也可

    public Map<String, String> getProperties() {
        return properties;
    }

    @JsonAnySetter
    public void add(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String toString() {
        return "JsonAnySetterBean{" +
                "name='" + name + '\'' +
                ", properties=" + properties +
                '}';
    }
}
