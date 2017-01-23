package com.gtw.jackson.model.generalAnnotations;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class GeneralBean {
    public int id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public Date eventDate;

    @JsonProperty("name")
    public void setTheName(String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public String getTheName() {
        return name;
    }

    public GeneralBean() {
    }

    public GeneralBean(int id, String name, Date eventDate) {
        this.id = id;
        this.name = name;
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "GeneralBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eventDate=" + eventDate +
                '}';
    }
}
