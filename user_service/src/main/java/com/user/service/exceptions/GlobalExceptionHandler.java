package com.user.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.user.service.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiResponse resp = ApiResponse.builder().message(message).success(true).httpStatus(HttpStatus.NOT_FOUND).build();
		return new ResponseEntity<ApiResponse>(resp, HttpStatus.NOT_FOUND);
	}
	
}
