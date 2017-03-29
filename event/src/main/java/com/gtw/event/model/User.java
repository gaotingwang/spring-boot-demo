package com.gtw.event.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -4980043793652576611L;

    private String username;
    private String password;
}
