package com.gtw.jackson.model.serialization;

import com.fasterxml.jackson.annotation.JsonValue;

public enum  JsonValueBean {
    TYPE1(1, "Type A"), TYPE2(2, "Type B");

    private Integer id;
    private String name;

    /**
     * JsonValue用于序列整个实例对象的单一方法
     */
    @JsonValue
    public String getName() {
        return name;
    }

    JsonValueBean(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
