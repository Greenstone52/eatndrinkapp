package com.onlineFoodOrdering.onlineFoodOrdering.service;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(String message) {
        super(message);
    }
}
