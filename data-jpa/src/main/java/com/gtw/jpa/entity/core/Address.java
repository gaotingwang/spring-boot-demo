package com.gtw.jpa.entity.core;

import com.gtw.jpa.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "address")
@AllArgsConstructor
public class Address extends AbstractEntity {
    private String country;
    private String city;
    private String street;

    /**
     * The default constructor only exists for the sake of JPA.
     * You won’t use it directly, so it is designated as protected.
     */
    protected Address(){

    }

    public Address getCopy() {
        return new Address(this.country, this.city, this.street);
    }
}
