package com.onlineFoodOrdering.onlineFoodOrdering.exception;

public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
