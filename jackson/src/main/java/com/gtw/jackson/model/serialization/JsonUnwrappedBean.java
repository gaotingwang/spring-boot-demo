package com.gtw.jackson.model.serialization;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class JsonUnwrappedBean {
    public int id;

    /**
     * JsonUnwrapped将对象解压到只显示属性
     */
    @JsonUnwrapped
    public Name name;

    public static class Name {
        public String firstName;
        public String lastName;

        public Name() {
        }

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "Name{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }

    public JsonUnwrappedBean() {
    }

    public JsonUnwrappedBean(int id, Name name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "JsonUnwrappedBean{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
