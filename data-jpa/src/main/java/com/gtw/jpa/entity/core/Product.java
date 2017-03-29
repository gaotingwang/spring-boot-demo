package com.gtw.jpa.entity.core;

import com.gtw.jpa.entity.AbstractEntity;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "product")
@Data
public class Product extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @CreatedDate// 需要在config中开启审计支持，对应的实体需要使用@EntityListeners(AuditingEntityListener.class)注解
    @Column(updatable = false)// 否则实体更新时，可能会被覆盖
    private Long createTime;

    @ElementCollection
    private Map<String, String> attributes = new HashMap<>();

    public Product(String name, BigDecimal price) {
        this(name, price, null);
    }

    public Product(String name, BigDecimal price, String description) {

        Assert.hasText(name, "Name must not be null or empty!");
        Assert.isTrue(BigDecimal.ZERO.compareTo(price) < 0, "Price must be greater than zero!");

        this.name = name;
        this.price = price;
        this.description = description;
    }

    protected Product() {

    }

    public void setAttribute(String name, String value) {

        Assert.hasText(name);

        if (value == null) {
            this.attributes.remove(value);
        } else {
            this.attributes.put(name, value);
        }
    }

    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }
}
