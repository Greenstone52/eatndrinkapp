package com.onlineFoodOrdering.onlineFoodOrdering.request;

import lombok.Data;

@Data
public class SetOwnerToARestaurantRequest {
    private String ownerPassword;
    private String restaurantPassword;
}
