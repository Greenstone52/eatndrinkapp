package com.onlineFoodOrdering.onlineFoodOrdering.request;

import lombok.Data;

@Data
public class CardCreateRequest {
    private short month;
    private short year;
    private String cardNumber;
    private short cvc;
    private String name;
}
