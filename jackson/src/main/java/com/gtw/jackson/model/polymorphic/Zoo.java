package com.gtw.jackson.model.polymorphic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

public class Zoo {
    public Animal animal;

    /**
     * JsonTypeInfo用来指示什么类型的信息的细节被包括在序列
     */
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
    //@JsonTypeInfo(use=Id.CLASS,include=As.PROPERTY,property="@class")
    /**
     * JsonSubTypes用于指示子类型注释类型的
     */
    @JsonSubTypes({
            @JsonSubTypes.Type(value = Dog.class, name = "dog"),
            @JsonSubTypes.Type(value = Cat.class, name = "cat")
    })
    public static class Animal {
        public String name;

        public Animal(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Animal{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    /**
     * JsonTypeName标注子类名称
     */
    @JsonTypeName("dog")
    public static class Dog extends Animal {
        public double barkVolume;

        @JsonCreator
        public Dog(@JsonProperty("name") String name) {
            super(name);
        }
    }

    @JsonTypeName("cat")
    public static class Cat extends Animal {
        boolean likesCream;
        public int lives;

        public Cat(String name) {
            super(name);
        }
    }

    @JsonCreator
    public Zoo(@JsonProperty("animal") Animal animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "Zoo{" +
                "animal=" + animal +
                '}';
    }
}
