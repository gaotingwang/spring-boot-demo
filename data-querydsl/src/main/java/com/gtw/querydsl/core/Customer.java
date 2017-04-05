package com.gtw.querydsl.core;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@QueryEntity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Customer extends AbstractEntity {

	private String firstname, lastname;

	private EmailAddress emailAddress;

	private Set<Address> addresses = new HashSet<Address>();

	public Customer(String firstname, String lastname) {

		Assert.hasText(firstname);
		Assert.hasText(lastname);

		this.firstname = firstname;
		this.lastname = lastname;
	}

	public void add(Address address) {
		this.addresses.add(address);
	}

	public Set<Address> getAddresses() {
		return Collections.unmodifiableSet(addresses);
	}
}
