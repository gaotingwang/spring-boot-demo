package com.gtw.es.model;

import java.util.List;

public class Page<T> {
    public static final Page EMPTY = new Page(null, null, 0, 0);

    private List<T> objects;
    private String input;
    private int from;
    private int size;

    public Page(List<T> objects, String input, int from, int size) {
        this.objects = objects;
        this.input = input;
        this.from = from;
        this.size = size;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
