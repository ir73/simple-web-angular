package com.sappadev.simplewebangular.controllers.exceptions;

import com.sappadev.AbstractContextControllerTests;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * user: sergeil
 * date: 2.11.2015
 */
public class GlobalExceptionHandlerTest extends AbstractContextControllerTests {

	@Test
	@WithMockUser("mikew")
	public void testHandleException() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/" + 2345636))
			   .andExpect(MockMvcResultMatchers.status().isBadRequest())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("ERROR")));
	}

	@Test
	public void testAccessDeniedException() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/" + 2345636))
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("ERROR")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("UNAUTHORIZED")));
	}
}