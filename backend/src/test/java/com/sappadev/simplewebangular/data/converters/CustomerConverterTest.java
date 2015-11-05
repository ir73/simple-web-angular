package com.sappadev.simplewebangular.data.converters;

import com.sappadev.AbstractContextControllerTests;
import com.sappadev.simplewebangular.data.domain.Customer;
import com.sappadev.simplewebangular.data.dto.CustomerDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * user: sergeil
 * date: 2.11.2015
 */
public class CustomerConverterTest extends AbstractContextControllerTests {

	@Autowired
	private CustomerConverter converter;

	@Test
	public void testConvert() throws Exception {
		CustomerDTO dto = new CustomerDTO();
		dto.setUsername("u");
		dto.setPassword("p");
		dto.setId(4L);
		dto.setLastName("l");
		dto.setFirstName("f");
		Customer c = converter.convert(dto);

		Assert.assertEquals("u", c.getUsername());
		Assert.assertEquals("p", c.getPassword());
		Assert.assertEquals((Long)4L, c.getId());
		Assert.assertEquals("l", c.getLastName());
		Assert.assertEquals("f", c.getFirstName());
	}

	@Test
	public void testUnconvert() throws Exception {
		Customer customer = new Customer();
		customer.setUsername("u");
		customer.setPassword("p");
		customer.setId(4L);
		customer.setLastName("l");
		customer.setFirstName("f");
		CustomerDTO dto = converter.unconvert(customer);

		Assert.assertEquals("u", dto.getUsername());
		Assert.assertEquals("p", dto.getPassword());
		Assert.assertEquals((Long) 4L, dto.getId());
		Assert.assertEquals("l", dto.getLastName());
		Assert.assertEquals("f", dto.getFirstName());
	}

	@Test
	public void testUnconvertList() throws Exception {
		Customer customer = new Customer();
		customer.setUsername("u");
		customer.setPassword("p");
		customer.setId(4L);
		customer.setLastName("l");
		customer.setFirstName("f");
		ArrayList<Customer> list = new ArrayList<>();
		list.add(customer);
		List<CustomerDTO> dtoList = converter.unconvert(list);

		Assert.assertEquals(1, dtoList.size());
		Assert.assertEquals("u", dtoList.get(0).getUsername());
		Assert.assertEquals("p", dtoList.get(0).getPassword());
		Assert.assertEquals((Long) 4L, dtoList.get(0).getId());
		Assert.assertEquals("l", dtoList.get(0).getLastName());
		Assert.assertEquals("f", dtoList.get(0).getFirstName());
	}
}