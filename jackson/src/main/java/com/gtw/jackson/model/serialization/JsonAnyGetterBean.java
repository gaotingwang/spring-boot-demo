package com.gtw.jackson.model.serialization;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.HashMap;
import java.util.Map;

public class JsonAnyGetterBean {
    public String name;
    private Map<String, String> properties;

    /**
     * JsonAnyGetter将Map key-value作为对象标准属性输出
     */
    @JsonAnyGetter
    public Map<String, String> getProperties() {
        return properties;
    }

    public JsonAnyGetterBean(String name) {
        this.name = name;
        this.properties = new HashMap<>();
    }

    public void add(String key, String value){
        properties.put(key, value);
    }
}
