package com.sappadev.simplewebangular.services;

import com.sappadev.AbstractContextControllerTests;
import com.sappadev.simplewebangular.data.domain.Customer;
import com.sappadev.simplewebangular.data.dto.CustomerDTO;
import org.exparity.hamcrest.date.DateMatchers;
import org.exparity.hamcrest.date.Months;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * user: sergeil
 * date: 2.11.2015
 */
public class CustomerServiceTest extends AbstractContextControllerTests {

	@Autowired
	private CustomerService customerService;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testGetAllCustomers() throws Exception {
		List<CustomerDTO> customers = customerService.getAllCustomers();

		Assert.assertEquals(2, customers.size());

//		(1, 'Sergei', 'Ledvanov', '1983-07-19', 'sergeil', '123'),
//		(2, 'Ted', 'Patrick', '2001-01-02', 'sponge', 'bob');
		Assert.assertThat(customers, Matchers.<CustomerDTO>hasItem(
				Matchers.allOf(
						Matchers.hasProperty("id", Matchers.is(1L)),
						Matchers.hasProperty("firstName", Matchers.is("Sergei")),
						Matchers.hasProperty("lastName", Matchers.is("Ledvanov")),
						Matchers.hasProperty("username", Matchers.is("sergeil")),
						Matchers.hasProperty("password", Matchers.is("123")),
						Matchers.hasProperty("dateOfBirth", DateMatchers.sameDay(1983, Months.JULY, 19))
				)
		));

		Assert.assertThat(customers, Matchers.<CustomerDTO>hasItem(
				Matchers.allOf(
						Matchers.hasProperty("id", Matchers.is(2L)),
						Matchers.hasProperty("firstName", Matchers.is("Ted")),
						Matchers.hasProperty("lastName", Matchers.is("Patrick")),
						Matchers.hasProperty("username", Matchers.is("sponge")),
						Matchers.hasProperty("password", Matchers.is("bob")),
						Matchers.hasProperty("dateOfBirth", DateMatchers.sameDay(2001, Months.JANUARY, 2))
				)
		));
	}

	@Test
	public void testSaveCustomer() throws Exception {

		Date dateOfBirth = new Date();

		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("F");
		customer.setLastName("L");
		customer.setPassword("Pwd");
		customer.setUsername("U");
		customer.setDateOfBirth(dateOfBirth);

		customerService.saveCustomer(customer);

		List<CustomerDTO> customers = customerService.getAllCustomers();

		Assert.assertEquals(3, customers.size());

		Assert.assertThat(customers, Matchers.<CustomerDTO>hasItem(
				Matchers.allOf(
						Matchers.hasProperty("id", Matchers.notNullValue()),
						Matchers.hasProperty("firstName", Matchers.is("F")),
						Matchers.hasProperty("lastName", Matchers.is("L")),
						Matchers.hasProperty("username", Matchers.is("U")),
						Matchers.hasProperty("password", Matchers.is("Pwd")),
						Matchers.hasProperty("dateOfBirth", DateMatchers.sameDay(dateOfBirth))
				)
		));

	}

	@Test
	public void testCreateCustomer() throws Exception {
		Date dateOfBirth = new Date();

		CustomerDTO customer = new CustomerDTO();
		customer.setFirstName("F");
		customer.setLastName("L");
		customer.setPassword("Pwd");
		customer.setUsername("U");
		customer.setDateOfBirth(dateOfBirth);

		customerService.saveCustomer(customer);

		List<CustomerDTO> customers = customerService.getAllCustomers();

		Assert.assertEquals(3, customers.size());

		Assert.assertThat(customers, Matchers.<CustomerDTO>hasItem(
				Matchers.allOf(
						Matchers.hasProperty("id", Matchers.notNullValue()),
						Matchers.hasProperty("firstName", Matchers.is("F")),
						Matchers.hasProperty("lastName", Matchers.is("L")),
						Matchers.hasProperty("username", Matchers.is("U")),
						Matchers.hasProperty("password", Matchers.is("Pwd")),
						Matchers.hasProperty("dateOfBirth", DateMatchers.sameDay(dateOfBirth))
				)
		));

	}

	@Test
	public void testDeleteCustomer() throws Exception {
		customerService.deleteCustomer(2L);

		List<CustomerDTO> customers = customerService.getAllCustomers();
		Assert.assertEquals(1, customers.size());

	}
}