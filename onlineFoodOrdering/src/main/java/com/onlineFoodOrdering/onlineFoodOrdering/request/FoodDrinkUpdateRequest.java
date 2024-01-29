package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import lombok.Data;

@Data
public class FoodDrinkUpdateRequest {
    private double salesPrice;
    private double costPrice;
    private String name;
}
