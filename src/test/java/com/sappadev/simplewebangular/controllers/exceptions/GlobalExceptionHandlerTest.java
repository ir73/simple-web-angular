package com.sappadev.simplewebangular.controllers.exceptions;

import com.sappadev.AbstractContextControllerTests;
import com.sappadev.simplewebangular.data.domain.Customer;
import com.sappadev.simplewebangular.data.dto.CustomerDTO;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.Assert.*;

/**
 * user: sergeil
 * date: 2.11.2015
 */
public class GlobalExceptionHandlerTest extends AbstractContextControllerTests {

	@Test
	@WithMockUser("sergeil")
	public void testHandleException() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + 2345636))
			   .andDo(MockMvcResultHandlers.print())
			   .andExpect(MockMvcResultMatchers.status().isBadRequest())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("ERROR")));
	}

	@Test
	public void testAccessDeniedException() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + 2345636))
			   .andDo(MockMvcResultHandlers.print())
			   .andExpect(MockMvcResultMatchers.status().isUnauthorized())
			   .andExpect(MockMvcResultMatchers.jsonPath("$.response", Matchers.is("ERROR")))
			   .andExpect(MockMvcResultMatchers.jsonPath("$.errorCode", Matchers.is("UNAUTHORIZED")));
	}
}