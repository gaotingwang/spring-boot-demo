package com.gtw.jackson.model.deserialization;

import com.fasterxml.jackson.annotation.JsonSetter;

public class JsonSetterBean {
    public int id;
    private String name;

    /**
     * JsonSetter反序列化中指定key值对应的setter方法
     */
    @JsonSetter("name")
    public void setTheName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "JsonSetterBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
