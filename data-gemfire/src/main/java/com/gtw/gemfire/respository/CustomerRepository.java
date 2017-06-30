package com.gtw.gemfire.respository;

import com.gtw.gemfire.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findByFirstname(String firstname);
    Customer findByLastname(String lastname);
    Iterable<Customer> findByAgeGreaterThan(int age);
    Iterable<Customer> findByAgeLessThan(int age);
}
