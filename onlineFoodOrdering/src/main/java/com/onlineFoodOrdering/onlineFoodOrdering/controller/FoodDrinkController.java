package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.request.FoodDrinkCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.FoodDrinkUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.service.FoodDrinkService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/foodDrinks")
public class FoodDrinkController {
    private FoodDrinkService foodDrinkService;

    @GetMapping("/{restaurantId}")
    public List<FoodDrink> getAllTheFoodDrinksOfTheRestaurant(@PathVariable Long restaurantId){
        return foodDrinkService.getAllTheFoodDrinksOfTheRestaurant(restaurantId);
    }

    @GetMapping("/{menuId}")
    public List<FoodDrink> getOneMenusFoodDrink(@PathVariable Long menuId){
        return foodDrinkService.getOneMenusFoodDrink(menuId);
    }

    @PostMapping("/{menuId}")
    public void addFoodDrink(@PathVariable Long menuId, @RequestBody FoodDrinkCreateRequest request) {
        foodDrinkService.addFoodDrink(menuId,request);
    }

    @PutMapping("/{foodDrinkId}")
    public void updateFoodDrink(@PathVariable Long foodDrinkId, @RequestBody FoodDrinkUpdateRequest request){
        foodDrinkService.updateFoodDrink(foodDrinkId,request);
    }

    @DeleteMapping("/{foodDrinkId}")
    public void deleteFoodDrink(@PathVariable Long foodDrinkId){
        foodDrinkService.deleteFoodDrink(foodDrinkId);
    }
}
