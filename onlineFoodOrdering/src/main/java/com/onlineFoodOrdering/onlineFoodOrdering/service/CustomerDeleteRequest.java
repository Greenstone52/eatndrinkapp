package com.onlineFoodOrdering.onlineFoodOrdering.service;

import lombok.Data;

@Data
public class CustomerDeleteRequest {
    private String password;
    private String verifyPassword;
}
