package com.gtw.jackson.model.serialization;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * JsonRootName指定序列化的包装根名称
 */
@JsonRootName(value = "user")
public class JsonRootNameBean {
    public int id;
    public String name;

    public JsonRootNameBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
