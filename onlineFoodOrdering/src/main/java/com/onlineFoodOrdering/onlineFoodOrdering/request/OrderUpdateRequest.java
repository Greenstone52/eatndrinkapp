package com.onlineFoodOrdering.onlineFoodOrdering.request;

import lombok.Data;

@Data
public class OrderUpdateRequest {
    private Long menuId;
    private Long foodDrinkId;
}
