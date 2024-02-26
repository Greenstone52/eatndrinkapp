package com.onlineFoodOrdering.onlineFoodOrdering.exception;

public class RestaurantIncorrectPasswordException extends RuntimeException{
    public RestaurantIncorrectPasswordException(String message){
        super(message);
    }
}
