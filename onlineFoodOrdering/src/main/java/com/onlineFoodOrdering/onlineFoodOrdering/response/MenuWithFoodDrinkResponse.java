package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import lombok.Data;

import java.util.List;

@Data
public class MenuWithFoodDrinkResponse {
    private String name;
    private List<FoodDrink> foodDrinkList;

    public MenuWithFoodDrinkResponse(Menu menu){
        this.name = menu.getName();

    }
}
