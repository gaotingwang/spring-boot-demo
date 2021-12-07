package com.gtw.es.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = ProductDeseralizer.class)
@JsonSerialize(using = ProductSerializer.class)
public class Product {

    private String id;
    private String name;
    private String description;
    private String color;
    private double price;
    private int stockAvailable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockAvailable() {
        return stockAvailable;
    }

    public void setStockAvailable(int stockAvailable) {
        this.stockAvailable = stockAvailable;
    }
}
