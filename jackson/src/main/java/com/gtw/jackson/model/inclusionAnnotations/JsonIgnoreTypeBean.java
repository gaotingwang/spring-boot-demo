package com.gtw.jackson.model.inclusionAnnotations;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Data;

@Data
public class JsonIgnoreTypeBean {

    private int id;
    private Name name;

    /**
     * JsonIgnoreType标记指定类型的所有属性被忽略
     */
    @Data
    @JsonIgnoreType
    public static class Name {
        private String firstName;
        private String lastName;

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    public JsonIgnoreTypeBean(int id, Name name) {
        this.id = id;
        this.name = name;
    }
}
