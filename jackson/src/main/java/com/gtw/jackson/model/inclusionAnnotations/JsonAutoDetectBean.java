package com.gtw.jackson.model.inclusionAnnotations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * JsonAutoDetect用于覆盖默认语义的哪些属性是可见的(现在启用序列化私有属性)
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class JsonAutoDetectBean {
    private int id;
    private String name;

    public JsonAutoDetectBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
