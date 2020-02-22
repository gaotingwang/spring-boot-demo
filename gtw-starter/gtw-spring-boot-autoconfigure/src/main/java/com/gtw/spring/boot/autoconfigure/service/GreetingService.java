package com.gtw.spring.boot.autoconfigure.service;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class GreetingService {

    private List<String> members = new ArrayList<>();

    public void sayHello(){
        members.forEach(s -> System.out.println("hello " + s));
    }
}
