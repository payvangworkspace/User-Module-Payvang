package com.Payvang.Login.CustomExceptions;

public class MerchantNotFoundException extends RuntimeException {
    public MerchantNotFoundException(String message) {
        super(message);
    }
}
