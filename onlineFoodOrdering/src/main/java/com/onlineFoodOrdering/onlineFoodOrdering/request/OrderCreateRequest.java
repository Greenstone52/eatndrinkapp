package com.onlineFoodOrdering.onlineFoodOrdering.request;

import lombok.Data;

@Data
public class OrderCreateRequest {
    //private Long restaurantId;
    //private Long menuId;
    private Long foodDrinkId;
    private String addressTitle;
}
