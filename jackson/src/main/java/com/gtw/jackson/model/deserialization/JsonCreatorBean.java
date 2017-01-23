package com.gtw.jackson.model.deserialization;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonCreatorBean {
    public int id;
    public String name;

    /**
     * JsonCreator调用构造函数进行反序列化
     */
    @JsonCreator
    public JsonCreatorBean(@JsonProperty("id") int id, @JsonProperty("theName") String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "JsonCreatorBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
