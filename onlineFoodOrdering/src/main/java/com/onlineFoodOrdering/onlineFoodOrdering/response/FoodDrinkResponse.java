package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import lombok.Data;

@Data
public class FoodDrinkResponse {
    private String name;
    private double salesPrice;

    public FoodDrinkResponse(FoodDrink foodDrink){
        this.name = foodDrink.getName();
        this.salesPrice = foodDrink.getSalesPrice();
    }
}
