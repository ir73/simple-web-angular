package com.sappadev.simplewebangular.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sappadev.AbstractContextControllerTests;
import com.sappadev.simplewebangular.data.dto.CustomerDTO;
import com.sappadev.simplewebangular.services.CustomerService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

/**
 * user: sergeil
 * date: 2.11.2015
 */
public class CustomerControllerTest extends AbstractContextControllerTests {

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private CustomerService customerService;

	@Before
	public void setUp() throws Exception {

	}

	@Test
	@WithMockUser(roles = "USER")
	public void testGetAllCustomers() throws Exception {
		final LocalDate dateTime = LocalDate.of(1983, Month.JULY, 19);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/"))
		       .andExpect(MockMvcResultMatchers.status().isOk())
		       .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("Mike")))
		       .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("Wilson")))
		       .andExpect(MockMvcResultMatchers.jsonPath("$[0].username", Matchers.is("mikew")))
		       .andExpect(MockMvcResultMatchers.jsonPath("$[0].password", Matchers.is("123")))
		       .andExpect(MockMvcResultMatchers.jsonPath("$[0].dateOfBirth",
		                                                 Matchers.is(dateTime.toString())));
	}

	@Test
	public void testGetAllCustomers_unauthorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/"))
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("ERROR")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("UNAUTHORIZED")));
	}

	@Test
	@WithMockUser("mikew")
	public void testSaveCustomer() throws Exception {
		LocalDate localDate = LocalDate.now();

		CustomerController.SaveCustomerRequestJson req = new CustomerController.SaveCustomerRequestJson();
		req.setDateOfBirth(localDate);
		req.setFirstName("New firstname");
		req.setLastName("New lastname");
		req.setId(2L);
		req.setPassword("New password");
		req.setUsername("New username");

		mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/" + 2)
											  .content(mapper.writeValueAsString(req))
											  .contentType(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id", Matchers.is(2)))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.customer.firstName", Matchers.is("New firstname")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.customer.lastName", Matchers.is("New lastname")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.customer.username", Matchers.is("New username")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.customer.password", Matchers.is("New password")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.customer.dateOfBirth", Matchers.is("")));

		List<CustomerDTO> customers = customerService.getAllCustomers();

		Assert.assertThat(customers, Matchers.<CustomerDTO>hasItem(
				Matchers.anyOf(
						Matchers.hasProperty("id", Matchers.is(2L)),
						Matchers.hasProperty("firstName", Matchers.is("New firstname")),
						Matchers.hasProperty("lastName", Matchers.is("New lastname")),
						Matchers.hasProperty("username", Matchers.is("New username")),
						Matchers.hasProperty("password", Matchers.is("New password")),
						Matchers.hasProperty("dateOfBirth", Matchers.is(""))
				)
				));

	}

	@Test
	public void testSaveCustomer_unauthorized() throws Exception {
		LocalDate localDate = LocalDate.now();

		CustomerController.SaveCustomerRequestJson req = new CustomerController.SaveCustomerRequestJson();
		req.setDateOfBirth(localDate);
		req.setFirstName("New firstname");
		req.setLastName("New lastname");
		req.setId(2L);
		req.setPassword("New password");
		req.setUsername("New username");

		mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/" + 2)
											  .content(mapper.writeValueAsString(req))
											  .contentType(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("ERROR")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("UNAUTHORIZED")));

		List<CustomerDTO> customers = customerService.getAllCustomers();

		Assert.assertThat(customers, Matchers.<CustomerDTO>hasItem(
				Matchers.anyOf(
						Matchers.hasProperty("id", Matchers.is(2L)),
						Matchers.hasProperty("firstName", Matchers.is("Ted")),
						Matchers.hasProperty("lastName", Matchers.is("Patrick")),
						Matchers.hasProperty("username", Matchers.is("sponge")),
						Matchers.hasProperty("password", Matchers.is("bob")),
						Matchers.hasProperty("dateOfBirth", Matchers.is(""))
				)
		));

	}

	@Test
	@WithMockUser("mikew")
	public void testCreateCustomer() throws Exception {
		LocalDate localDate = LocalDate.now();

		CustomerController.CreateCustomerRequestJson req = new CustomerController.CreateCustomerRequestJson();
		req.setDateOfBirth(localDate);
		req.setFirstName("New firstname1");
		req.setLastName("New lastname1");
		req.setPassword("New password1");
		req.setUsername("New username1");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/")
				.content(mapper.writeValueAsString(req))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.customer.id", Matchers.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.customer.firstName", Matchers.is("New firstname1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.customer.lastName", Matchers.is("New lastname1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.customer.username", Matchers.is("New username1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.customer.password", Matchers.is("New password1")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.customer.dateOfBirth", Matchers.is("")));

		List<CustomerDTO> customers = customerService.getAllCustomers();

		Assert.assertThat(customers, Matchers.<CustomerDTO>hasItem(
				Matchers.anyOf(
						Matchers.hasProperty("id", Matchers.notNullValue()),
						Matchers.hasProperty("firstName", Matchers.is("New firstname1")),
						Matchers.hasProperty("lastName", Matchers.is("New lastname1")),
						Matchers.hasProperty("username", Matchers.is("New username1")),
						Matchers.hasProperty("password", Matchers.is("New password1")),
						Matchers.hasProperty("dateOfBirth", Matchers.is(""))
				)
		));
	}

	@Test
	public void testCreateCustomer_unauthorized() throws Exception {
		LocalDate localDate = LocalDate.of(1999, Month.AUGUST, 1);

		CustomerController.CreateCustomerRequestJson req = new CustomerController.CreateCustomerRequestJson();
		req.setDateOfBirth(localDate);
		req.setFirstName("New firstname1");
		req.setLastName("New lastname1");
		req.setPassword("New password1");
		req.setUsername("New username1");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/")
											  .content(mapper.writeValueAsString(req))
											  .contentType(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("ERROR")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("UNAUTHORIZED")));

		List<CustomerDTO> customers = customerService.getAllCustomers();

		// same amount of customers as before
		Assert.assertEquals(2, customers.size());
	}

	@Test
	@WithMockUser("mikew")
	public void testDeleteCustomer() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/" + 2))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("OK")));

		List<CustomerDTO> customers = customerService.getAllCustomers();

		Assert.assertEquals(1, customers.size());
	}

	@Test
	@WithMockUser("mikew")
	public void testDeleteCustomer_nonExistentId() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/" + 2345636))
			   .andExpect(MockMvcResultMatchers.status().isBadRequest())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("ERROR")));

		List<CustomerDTO> customers = customerService.getAllCustomers();

		Assert.assertEquals(2, customers.size());
	}
}