package com.gtw.jackson.model.serialization;

import com.fasterxml.jackson.annotation.JsonRawValue;

public class JsonRawValueBean {
    public String name;

    /**
     * 嵌入一些定制的实体json字符串
     */
    @JsonRawValue
    public String json;

    public JsonRawValueBean(String name, String json) {
        this.name = name;
        this.json = json;
    }
}
