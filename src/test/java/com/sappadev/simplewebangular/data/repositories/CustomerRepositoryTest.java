package com.sappadev.simplewebangular.data.repositories;

import com.sappadev.AbstractContextControllerTests;
import com.sappadev.simplewebangular.data.domain.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * user: sergeil
 * date: 2.11.2015
 */
public class CustomerRepositoryTest extends AbstractContextControllerTests {

	@Autowired
	private CustomerRepository customerRepository;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testFindCustomerByUsername() throws Exception {
		Customer customer = customerRepository.findCustomerByUsername("sponge");
		Assert.assertEquals("sponge", customer.getUsername());
	}
}