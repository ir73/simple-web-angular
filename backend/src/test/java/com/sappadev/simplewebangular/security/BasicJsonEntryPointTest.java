package com.sappadev.simplewebangular.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sappadev.AbstractContextControllerTests;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AccountExpiredException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;

import static org.junit.Assert.*;

/**
 * user: sergeil
 * date: 2.11.2015
 */
public class BasicJsonEntryPointTest extends AbstractContextControllerTests {

	private ObjectMapper mapper = new ObjectMapper();

	@Mock
	private HttpServletRequest httpServletRequest;

	@Mock
	private HttpServletResponse httpServletResponse;

	private BasicJsonEntryPoint entryPoint;

	@Before
	public void setup() {
		entryPoint = new BasicJsonEntryPoint();
	}

	@Test
	public void testCommence() throws Exception {
		File file = File.createTempFile("tdfgdf", "sfg");

		PrintWriter printWriter = new PrintWriter(file);
		Mockito.when(httpServletResponse.getWriter()).thenReturn(printWriter);
		entryPoint.commence(httpServletRequest, httpServletResponse, new AccountExpiredException("message"));

		printWriter.flush();
		Mockito.verify(httpServletResponse).setStatus(HttpStatus.UNAUTHORIZED.value());

		Assert.assertEquals("{\"response\":\"ERROR\",\"errorCode\":\"UNAUTHORIZED\"}",
							FileUtils.readFileToString(file));
		file.delete();
	}
}