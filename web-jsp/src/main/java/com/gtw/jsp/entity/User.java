package com.gtw.jsp.entity;

import lombok.Value;

@Value
public class User {
    private int userId;
    private String userName;
    private String sex;
    private int age;
}
