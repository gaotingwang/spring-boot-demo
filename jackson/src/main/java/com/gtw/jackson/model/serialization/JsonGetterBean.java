package com.gtw.jackson.model.serialization;

import com.fasterxml.jackson.annotation.JsonGetter;

public class JsonGetterBean {
    public int id;
    private String name;

    /**
     * JsonGetter 将一个特殊方法标注为指定属性的Getter方法
     */
    @JsonGetter("name")//@JsonProperty一样的功效
    public String getTheName() {
        return "hello " + name;
    }

    public JsonGetterBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
