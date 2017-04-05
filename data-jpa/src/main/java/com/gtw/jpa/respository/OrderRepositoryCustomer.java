package com.gtw.jpa.respository;

import com.gtw.jpa.entity.order.Order;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface OrderRepositoryCustomer extends Repository<Order, Long> {

    List<Tuple> find(Predicate predicate);

    QueryResults<Tuple> findPage(Pageable pageable);
}
