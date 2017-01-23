package com.gtw.jackson.model.deserialization;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.Date;

public class JsonDeserializeBean {
    public String name;

    /**
     * JsonDeserialize自定义反序列化方法
     */
    @JsonDeserialize(using = CustomDateDeserializer.class)
    public Date eventDate;

    @Override
    public String toString() {
        return "JsonDeserializeBean{" +
                "name='" + name + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
