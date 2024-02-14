package com.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	
	//we can add extra properties to manage
	
	public ResourceNotFoundException() {
		super("Resource not found on server");
	}
	
	public ResourceNotFoundException(String msg) {
		super(msg);
	}
	
}
