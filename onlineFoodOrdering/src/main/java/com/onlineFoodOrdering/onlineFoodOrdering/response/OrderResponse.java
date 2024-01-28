package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Order;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import lombok.Data;

@Data
public class OrderResponse {

    private FoodDrinkRepository foodDrinkRepository;

    private String foodOrDrinkName;
    private String restaurantName;
    public OrderResponse(Order order){
        FoodDrink foodDrink = foodDrinkRepository.findById(order.getFoodDrinkId()).orElse(null);

        if(foodDrink != null){
            foodOrDrinkName = foodDrink.getName();
        }

        if(order.getRestaurant() != null){
            restaurantName = order.getRestaurant().getName();
        }
    }
}
