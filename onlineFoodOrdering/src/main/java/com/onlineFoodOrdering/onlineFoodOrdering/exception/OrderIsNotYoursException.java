package com.onlineFoodOrdering.onlineFoodOrdering.exception;

public class OrderIsNotYoursException extends RuntimeException {
    public OrderIsNotYoursException(String string) {
        super(string);
    }
}
