package com.onlineFoodOrdering.onlineFoodOrdering.exception;

public class OrderCannotBeCancelledException extends RuntimeException {
    public OrderCannotBeCancelledException(String string) {
        super(string);
    }
}
