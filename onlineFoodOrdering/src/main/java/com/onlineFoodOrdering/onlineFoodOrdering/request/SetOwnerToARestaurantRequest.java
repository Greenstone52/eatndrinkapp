package com.onlineFoodOrdering.onlineFoodOrdering.request;

import lombok.Data;

@Data
public class SetOwnerToARestaurantRequest {
    private String ownerPW;
    private String restaurantPW;
}
