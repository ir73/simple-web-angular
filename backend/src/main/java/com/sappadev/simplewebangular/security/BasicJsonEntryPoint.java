package com.sappadev.simplewebangular.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sappadev.simplewebangular.controllers.vo.ErrorCode;
import com.sappadev.simplewebangular.controllers.vo.ErrorResponseJson;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
/**
 * Authentication entry point sends JSON unauthenticated error instead of servlet container's default
 * HTML content
 */
public class BasicJsonEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ErrorResponseJson errorResponseJson = new ErrorResponseJson(ErrorCode.UNAUTHORIZED);
        String errorResponseString = objectMapper.writeValueAsString(errorResponseJson);
        IOUtils.write(errorResponseString, response.getWriter());
    }
}
