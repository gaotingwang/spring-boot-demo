package com.gtw.jackson.model.inclusionAnnotations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * JsonIgnoreProperties类级别的
 * 反序列化时json字符串中key值对应的属性在对象中不存在时：@JsonIgnoreProperties(ignoreUnknown = true)
 */
@JsonIgnoreProperties({"sex"})
public class JsonIgnoreBean {
    /**
     * JsonIgnore属性级别的，不参与序列化
     */
    @JsonIgnore
    public int id;

    public String name;

    public String sex;

    public JsonIgnoreBean(int id, String name, String sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }
}
