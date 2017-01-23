package com.gtw.jackson.model.serialization;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class JsonSerializeBean {
    public String name;

    /**
     * JsonSerialize自定义序列化方式
     */
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date eventDate;

    public JsonSerializeBean(String name, Date eventDate) {
        this.name = name;
        this.eventDate = eventDate;
    }
}
