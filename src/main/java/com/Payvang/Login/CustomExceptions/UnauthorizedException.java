package com.Payvang.Login.CustomExceptions;

public class UnauthorizedException extends RuntimeException{
	
	private static final long serialVersionUID = 3L;

	public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
