package com.Payvang.Login.CustomExceptions;

public class InvalidRequestException extends RuntimeException {
	
	private static final long serialVersionUID = 2L;

	public InvalidRequestException(String message) {
	        super(message);
	    }
}
