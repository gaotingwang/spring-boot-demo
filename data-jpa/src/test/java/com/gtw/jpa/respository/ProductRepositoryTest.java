package com.gtw.jpa.respository;

import com.gtw.jpa.entity.core.Product;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Commit
    public void createProduct() {

        Product product = new Product("Camera bag", new BigDecimal(49.99));
        product = productRepository.save(product);

        assertNotNull(product.getId());
    }

    @Test
    public void findByDescriptionContaining() throws Exception {
        Pageable pageable = new PageRequest(0, 1, Sort.Direction.DESC, "name");
        Page<Product> page = productRepository.findByDescriptionContaining("Apple", pageable);

        assertEquals(1, page.getContent().size());
        assertEquals(2, page.getTotalElements());
        assertEquals(true, page.isFirst());
        assertEquals(true, page.hasNext());
    }

    @Test
    public void findByAttributeAndValue() throws Exception {
        List<Product> products = productRepository.findByAttributeAndValue("connector", "plug");

        assertNotNull(products);
    }

}