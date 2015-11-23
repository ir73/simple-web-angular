package com.sappadev.simplewebangular.controllers;

import com.sappadev.AbstractContextControllerTests;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * user: sergeil
 * date: 2.11.2015
 */
public class AuthControllerTest extends AbstractContextControllerTests {

	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testUser_unauthorized() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("ERROR")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("UNAUTHORIZED")));
	}

	@WithMockUser("mikew")
	@Test
	public void testUser_success() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/user"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("OK")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("mikew")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.authorities[0].authority", Matchers.is("ROLE_USER")));
	}
}