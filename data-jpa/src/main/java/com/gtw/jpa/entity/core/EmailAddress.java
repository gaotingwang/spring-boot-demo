package com.gtw.jpa.entity.core;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Pattern;

@Embeddable// 持久化层会将其属性放到包含它的类所对应的表中
public class EmailAddress {
    private static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    @Column(name = "email")
    private String value;

    public EmailAddress(String value) {
        Assert.isTrue(isValid(value), "Invalid email address!");
        this.value = value;
    }

    public boolean isValid(String candidate) {
        return PATTERN.matcher(candidate).matches();
    }

    /**
     * The default constructor only exists for the sake of JPA.
     * You won’t use it directly, so it is designated as protected.
     */
    protected EmailAddress() {
    }

    public String getValue() {
        return value;
    }
}
