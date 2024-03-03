package com.onlineFoodOrdering.onlineFoodOrdering.request;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String string) {
        super(string);
    }
}
