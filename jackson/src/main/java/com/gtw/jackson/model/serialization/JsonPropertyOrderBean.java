package com.gtw.jackson.model.serialization;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * JsonPropertyOrder按照指定顺序输出json字符串
 */
@JsonPropertyOrder({ "name", "id" })
public class JsonPropertyOrderBean {
    public int id;
    public String name;

    public JsonPropertyOrderBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
