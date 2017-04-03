package com.gtw.jpa.respository;

import com.gtw.jpa.entity.core.Customer;
import com.gtw.jpa.entity.core.EmailAddress;
import com.gtw.jpa.entity.core.Product;
import com.gtw.jpa.entity.order.LineItem;
import com.gtw.jpa.entity.order.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Commit
    public void createOrder() {

        Customer dave = customerRepository.findByEmailAddress(new EmailAddress("gaotingwang@qq.com"));
        Product iPad = productRepository.findOne(1L);

        Order order = new Order(dave, dave.getAddresses().iterator().next());
        order.add(new LineItem(iPad));

        order = orderRepository.save(order);
        assertNotNull(order.getId());
    }

    @Test
    public void findByCustomer() throws Exception {
        Customer dave = customerRepository.findByEmailAddress(new EmailAddress("gaotingwang@qq.com"));
        List<Order> orders = orderRepository.findByCustomer(dave);

        assertEquals(1, orders.size());
    }

}