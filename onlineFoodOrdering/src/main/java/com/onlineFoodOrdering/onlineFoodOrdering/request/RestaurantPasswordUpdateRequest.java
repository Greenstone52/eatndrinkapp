package com.onlineFoodOrdering.onlineFoodOrdering.request;

import lombok.Data;

@Data
public class RestaurantPasswordUpdateRequest {
    private String oldPassword;
    private String newPassword;
    private String newPassword2;
}
