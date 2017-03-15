package com.gtw.jpa.entity;

import static org.hamcrest.Matchers.*;

import com.gtw.jpa.entity.core.Product;
import com.gtw.jpa.entity.order.LineItem;
import com.gtw.jpa.entity.order.Order;
import org.hamcrest.Matcher;

public class OrderMatchers {

	/**
	 * Matches if the source {@link Iterable} has an {@link Order} that matches the given {@link Matcher}.
	 * 
	 * @param matcher must not be {@literal null}.
	 * @return
	 */
	public static <T> Matcher<Iterable<? super T>> containsOrder(Matcher<? super T> matcher) {
		return hasItem(matcher);
	}

	/**
	 * Matches if the {@link Order} has a {@link LineItem} matching the given {@link Matcher}.
	 * 
	 * @param matcher must not be {@literal null}.
	 * @return
	 */
	public static Matcher<Order> LineItem(Matcher<LineItem> matcher) {
		return hasProperty("lineItems", hasItem(matcher));
	}

	/**
	 * Matches if the {@link LineItem} refers to a {@link Product} that matches the given {@link Matcher}.
	 * 
	 * @param matcher must not be {@literal null}.
	 * @return
	 */
	public static Matcher<LineItem> Product(Matcher<Product> matcher) {
		return hasProperty("product", matcher);
	}
}
