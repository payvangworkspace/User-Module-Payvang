package com.Payvang.Login.CustomExceptions;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String message) {
		super(message);
	}
	public UserNotFoundException() {
		super("User not found on server");
	}
}
