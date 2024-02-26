package com.onlineFoodOrdering.onlineFoodOrdering.exception;

public class MenuNotFoundException extends RuntimeException {
    public MenuNotFoundException(String string) {
        super(string);
    }
}
