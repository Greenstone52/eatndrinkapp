package com.onlineFoodOrdering.onlineFoodOrdering.request;

import lombok.Data;

@Data
public class CustomerDeleteRequest {
    private String password;
    private String verifyPassword;
}
