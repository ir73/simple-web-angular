package com.sappadev.simplewebangular.controllers.exceptions;

import com.sappadev.simplewebangular.controllers.vo.ErrorCode;
import com.sappadev.simplewebangular.controllers.vo.ErrorResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
/**
 * Global exception handler to handle all exceptions in the app
 */
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	public GlobalExceptionHandler() {
		LOGGER.info("Initializing global exception handler");
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	/**
	 * Handles any exception
	 */
	public ErrorResponseJson handleException(Exception ex) {
		LOGGER.info("Generic exception handler fired: {}", ex.getMessage());
		return new ErrorResponseJson(ErrorCode.UNKNOWN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	/**
	 * Handles unauthorized exception
	 */
	public ErrorResponseJson handleAccessDeniedException(AccessDeniedException ex) {
		LOGGER.info("Access denied exception handler fired: {}", ex.getMessage());
		return new ErrorResponseJson(ErrorCode.UNAUTHORIZED);
	}

}
