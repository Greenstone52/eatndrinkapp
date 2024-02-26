package com.onlineFoodOrdering.onlineFoodOrdering.exception;

public class RestaurantAlreadyExistsException extends RuntimeException {
    public RestaurantAlreadyExistsException(String string) {
        super(string);
    }
}
