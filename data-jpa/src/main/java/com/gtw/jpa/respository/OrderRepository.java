package com.gtw.jpa.respository;

import com.gtw.jpa.entity.core.Customer;
import com.gtw.jpa.entity.order.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {

    // select o from Order o where o.customer = ?
    List<Order> findByCustomer(Customer customer);
}
