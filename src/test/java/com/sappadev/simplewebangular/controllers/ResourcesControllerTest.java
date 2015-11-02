package com.sappadev.simplewebangular.controllers;

import com.sappadev.AbstractContextControllerTests;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Created by serge_000 on 2.11.2015.
 */
public class ResourcesControllerTest extends AbstractContextControllerTests {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testMessages() throws Exception {
        this.mockMvc.perform(
                post("/res/messages/")
                        .param("lang", "en")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.['home.btn_add']", Matchers.is("Add")));
    }

    @Test
    public void testMessages_unknownLanguage() throws Exception {
        this.mockMvc.perform(
                post("/res/messages/")
                        .param("lang", "fi")
                        .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.['home.btn_add']", Matchers.is("Add")));
    }
}