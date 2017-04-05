package com.gtw.jpa.respository;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import com.gtw.jpa.entity.core.Product;
import com.gtw.jpa.entity.core.QProduct;
import com.querydsl.core.types.Predicate;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QuerydslProductRepositoryTest {

	static final QProduct product = QProduct.product;

	@Autowired
	ProductRepository repository;

	@Test
	public void findProductsByQuerydslPredicate() {

		Product iPad = repository.findOne(product.name.eq("Camera bag"));
		Predicate tablets = product.description.contains("tablet");

		Iterable<Product> result = repository.findAll(tablets);
		assertThat(result, is(Matchers.iterableWithSize(1)));
		assertThat(result, hasItem(iPad));
	}
}
