package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import lombok.Data;

@Data
public class FoodDrinkCreateRequest {
    private String name;
    private double salesPrice;
    private double costPrice;
    private Menu menu;
}
