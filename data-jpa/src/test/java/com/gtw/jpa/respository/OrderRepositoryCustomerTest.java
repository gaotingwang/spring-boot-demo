package com.gtw.jpa.respository;

import com.gtw.jpa.entity.core.QCustomer;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class OrderRepositoryCustomerTest {
    @Autowired
    private OrderRepositoryCustomer orderRepositoryCustomer;

    @Test
    public void find() throws Exception {
        List<Tuple> tuples =  orderRepositoryCustomer.find(QCustomer.customer.lastName.ne("高"));
        System.out.println(tuples);
    }

    @Test
    public void findPage() throws Exception {
        PageRequest pageRequest = new PageRequest(0,10);
        QueryResults<Tuple> tuples = orderRepositoryCustomer.findPage(pageRequest);
        System.out.println(tuples);
    }
}