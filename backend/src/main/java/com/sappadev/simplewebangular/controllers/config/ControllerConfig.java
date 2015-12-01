package com.sappadev.simplewebangular.controllers.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.sappadev.simplewebangular.controllers.vo.ErrorCode;
import com.sappadev.simplewebangular.controllers.vo.ErrorResponseJson;

@ControllerAdvice
@Slf4j
/**
 * sergeil, 1.12.2015
 */
public class ControllerConfig {

	public ControllerConfig() {
		log.info("Initializing controller config");
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	/**
	 * Handles any exception
	 */
	public ErrorResponseJson handleException(Exception ex) {
		log.info("Generic exception handler fired: {}", ex.getMessage());
		return new ErrorResponseJson(ErrorCode.UNKNOWN);
	}

	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	/**
	 * Handles unauthorized exception
	 */
	public ErrorResponseJson handleAccessDeniedException(AccessDeniedException ex) {
		log.info("Access denied exception handler fired: {}", ex.getMessage());
		return new ErrorResponseJson(ErrorCode.UNAUTHORIZED);
	}

}
