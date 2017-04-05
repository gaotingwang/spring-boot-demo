package com.gtw.querydsl.core;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static com.querydsl.collections.CollQueryFactory.*;

/**
 * @author 高廷旺
 * 创建原因: 集合查询 querydsl-collections
 */
public class ProductTest {

    private static final QProduct $ = QProduct.product;

    Product macBook, iPad, iPod, turntable;
    List<Product> products;
    @Before
    public void setUp() {

        macBook = new Product("MacBook Pro", "Apple laptop");
        iPad = new Product("iPad", "Apple tablet");
        iPod = new Product("iPod", "Apple MP3 player");
        turntable = new Product("Turntable", "Vinyl player");

        products = Arrays.asList(macBook, iPad, iPod, turntable);
    }

    /**
     * from()方法是CollQueryFactory提供的静态方法
     */
    @Test
    public void findsAllAppleProducts() {
        List<Product> result = from($, products).where($.description.contains("Apple")).fetch();

        assertThat(result, hasSize(3));
        assertThat(result, hasItems(macBook, iPad, iPod));
    }

    @Test
    public void findsAllAppleProductNames() {
        List<String> result = from($, products).select($.name).where($.description.contains("Apple")).fetch();

        assertThat(result, hasSize(3));
        assertThat(result, hasItems(macBook.getName(), iPad.getName(), iPod.getName()));
    }

    @Test
    public void findsPlayers() {
        List<Product> result = from($, products).where($.description.contains("player")).fetch();

        assertThat(result, hasSize(2));
        assertThat(result, hasItems(iPod, turntable));
    }
}