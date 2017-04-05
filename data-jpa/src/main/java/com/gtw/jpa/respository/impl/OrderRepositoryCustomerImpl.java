package com.gtw.jpa.respository.impl;

import com.gtw.jpa.entity.core.QCustomer;
import com.gtw.jpa.entity.core.QProduct;
import com.gtw.jpa.entity.order.QLineItem;
import com.gtw.jpa.entity.order.QOrder;
import com.gtw.jpa.respository.OrderRepositoryCustomer;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OrderRepositoryCustomerImpl implements OrderRepositoryCustomer {
    @Inject
    private Provider<EntityManager> em;

    private static final QOrder ORDER = QOrder.order;
    private static final QCustomer orderCustomer = QCustomer.customer;
    private static final QLineItem item = new QLineItem("item");
    private static final QProduct orderProduct = new QProduct("orderProduct");

    public List<Tuple> find(Predicate predicate) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        /* 查询DTO
        List<OrderDTO> orderDTOs = jpaQueryFactory.select(Projections.constructor(OrderDTO.class, ORDER.id, PRODUCT.name))
                .from(ORDER)...
        */

        // 条件查询用户订购商品
        JPAQuery<Tuple> jpaQuery = jpaQueryFactory.select(ORDER, orderCustomer, orderProduct)
                .from(ORDER)
                .leftJoin(ORDER.customer, orderCustomer)// 需要指定别名
                .leftJoin(ORDER.lineItems, item)
                .leftJoin(item.product, orderProduct);

        //添加查询条件
        jpaQuery.where(predicate);

        //拿到结果
        return jpaQuery.fetch();

    }

    @Override
    public QueryResults<Tuple> findPage(Pageable pageable) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        // 分页查询
        JPAQuery<Tuple> jpaQuery = queryFactory.select(ORDER.id,orderCustomer, orderProduct)
                .from(ORDER)
                .leftJoin(ORDER.customer, orderCustomer)
                .leftJoin(ORDER.lineItems, item)
                .leftJoin(item.product, orderProduct)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        //添加查询条件
        jpaQuery.where(orderCustomer.lastName.like("高"));

        return jpaQuery.fetchResults();
    }
}
