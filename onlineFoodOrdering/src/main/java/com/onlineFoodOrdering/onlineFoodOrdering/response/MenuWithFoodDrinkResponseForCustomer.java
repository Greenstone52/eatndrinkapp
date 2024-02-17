package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuWithFoodDrinkResponseForCustomer {
    private String name;
    private List<FoodDrinkResponse> foodDrinkListResponse;

    public MenuWithFoodDrinkResponseForCustomer(Menu menu){
        this.name = menu.getName();
    }
}
