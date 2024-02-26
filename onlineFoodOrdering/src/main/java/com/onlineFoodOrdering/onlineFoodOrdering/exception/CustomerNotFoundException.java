package com.onlineFoodOrdering.onlineFoodOrdering.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
