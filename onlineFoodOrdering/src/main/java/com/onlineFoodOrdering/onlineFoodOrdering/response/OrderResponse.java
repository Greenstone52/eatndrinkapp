package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Order;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import lombok.Data;

@Data
public class OrderResponse {
    private String foodOrDrinkName;
    private String restaurantName;
    public OrderResponse(Order order){
        this.foodOrDrinkName = order.getFoodDrink().getName();
        this.restaurantName = order.getRestaurant().getName();
    }
}
