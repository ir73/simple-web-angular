package com.sappadev.simplewebangular.res;

import com.sappadev.AbstractContextControllerTests;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;
import java.util.Properties;

import static org.junit.Assert.*;

/**
 * user: sergeil
 * date: 2.11.2015
 */
public class CustomMessageSourceTest extends AbstractContextControllerTests {

	@Test
	public void testGetAllMessages() throws Exception {
		CustomMessageSource messageSource = new CustomMessageSource();
		messageSource.setBasenames("classpath:messages/msg");
		Properties allProperties = messageSource.getAllMessages(new Locale("en"));

		Assert.assertEquals("Password", allProperties.get("home.customers.password"));
	}
}