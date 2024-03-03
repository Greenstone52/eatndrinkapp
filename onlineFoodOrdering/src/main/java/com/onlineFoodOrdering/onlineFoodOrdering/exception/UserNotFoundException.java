package com.onlineFoodOrdering.onlineFoodOrdering.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String string) {
        super(string);
    }
}
