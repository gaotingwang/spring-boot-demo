package com.gtw.jpa.respository;

import com.gtw.jpa.entity.core.Address;
import com.gtw.jpa.entity.core.Customer;
import com.gtw.jpa.entity.core.EmailAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void save() throws Exception {
        Customer customer = new Customer("廷旺", "高", new EmailAddress("gaotingwang@qq.com"));
        customer.add(new Address("27 Broadway", "New York", "United States"));
        Customer result = customerRepository.save(customer);

        assertNotNull(result.getId());
    }

    @Test
    public void findByEmailAddress() throws Exception {
        Customer customer = customerRepository.findByEmailAddress(new EmailAddress("gaotingwang@qq.com"));

        assertNotNull(customer);
        assertEquals("gaotingwang@qq.com", customer.getEmailAddress().getValue());
    }

}