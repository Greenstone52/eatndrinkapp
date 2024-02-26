package com.onlineFoodOrdering.onlineFoodOrdering.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String message){
        super(message);
    }
}
