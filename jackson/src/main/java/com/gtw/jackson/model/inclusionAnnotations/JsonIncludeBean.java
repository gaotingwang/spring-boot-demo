package com.gtw.jackson.model.inclusionAnnotations;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * JsonInclude参与序列化的级别(此处为非空)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonIncludeBean {
    public int id;
    public String name;

    public JsonIncludeBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
