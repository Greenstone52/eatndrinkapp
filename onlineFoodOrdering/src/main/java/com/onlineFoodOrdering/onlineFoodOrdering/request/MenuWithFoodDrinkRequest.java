package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import lombok.Data;

import java.util.List;

@Data
public class MenuWithFoodDrinkRequest {
    private String name;
    private List<FoodDrink> foodDrinkList;
}
