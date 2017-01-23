package com.gtw.jackson.model.deserialization;

import com.fasterxml.jackson.annotation.JacksonInject;

public class JsonInjectBean {
    /**
     * JacksonInject该值通过注入获得，它的值不从JSON数据
     */
    @JacksonInject
    public int id;

    public String name;

    @Override
    public String toString() {
        return "JsonInjectBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
