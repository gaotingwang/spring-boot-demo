package com.gtw.jpa.entity.user;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Manager extends Employee {

    private String title;

    @ElementCollection
    @CollectionTable(
            name="emp_phone",
            joinColumns=@JoinColumn(name="employeeId")
    )
    private List<Phone> phones;
}
