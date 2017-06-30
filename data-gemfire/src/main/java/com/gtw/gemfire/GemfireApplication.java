package com.gtw.gemfire;

import com.gtw.gemfire.model.Customer;
import com.gtw.gemfire.respository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * Created by gaotingwang on 17/6/30.
 */
@SpringBootApplication
@EnableGemfireRepositories
public class GemfireApplication implements CommandLineRunner {

    @Autowired
    CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(GemfireApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        Customer peter = new Customer("Peter", "Williams", 20);
        Customer mary = new Customer("Mary", "Diaz", 25);

        // SAVE customer to Gemfire
        customerRepository.save(peter);
        customerRepository.save(mary);

        // FindAll Customers
        System.out.println("-------Find All Customers-------");
        Iterable<Customer> custList = customerRepository.findAll();
        for(Customer c: custList){
            System.out.println(c.toString());
        }

        // Find a Customer by firstname
        System.out.println("-------Find Customer by FirstName-------");
        Customer c1 = customerRepository.findByFirstname("Peter");
        System.out.println(c1.toString());

        // Find a Customer by lastname
        System.out.println("-------Find Customer by LastName-------");
        Customer c2 = customerRepository.findByLastname("Diaz");
        System.out.println(c2.toString());


        // find customer by age
        System.out.println("-------Find Customers have age greater than 22-------");
        Iterable<Customer> custLstByAgeGreaterThan22 = customerRepository.findByAgeGreaterThan(22);
        for(Customer c: custLstByAgeGreaterThan22){
            System.out.println(c.toString());
        }


        System.out.println("-------Find Customers have age less than 30-------");
        Iterable<Customer> custLstByAgeLessThan30 = customerRepository.findByAgeLessThan(30);
        for(Customer c: custLstByAgeLessThan30){
            System.out.println(c.toString());
        }
    }
}
