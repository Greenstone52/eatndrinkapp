package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.MenuNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.RestaurantNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.response.MenuWithFoodDrinkResponseForCustomer;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantInfoResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.ReviewResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.FoodDrinkService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.MenuService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.RestaurantService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private FoodDrinkService foodDrinkService;
    private MenuService menuService;
    private RestaurantService restaurantService;
    private ReviewService reviewService;

    // FoodDrink
    @GetMapping("/foodDrinks/{menuId}")
    public List<FoodDrink> getOneMenusFoodDrink(@PathVariable Long menuId){
        return foodDrinkService.getOneMenusFoodDrink(menuId);
    }

    @GetMapping("/menus/{restaurantId}")
    public List<MenuWithFoodDrinkResponseForCustomer> getAllTheMenuOfTheRestaurantForCustomers(@PathVariable Long restaurantId){
        return menuService.getAllTheMenuOfTheRestaurantForCustomers(restaurantId);
    }

    // Restaurant
    @GetMapping("/restaurants")
    public List<RestaurantResponse> getAllTheRestaurants(@RequestParam(required = false) String type){
        return restaurantService.getAllTheRestaurants(type);
    }

    @GetMapping("/restaurants/{restaurantId}/info")
    public RestaurantInfoResponse getRestaurantInfo(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurantInfo(restaurantId);
    }

    // Review
    @GetMapping("/reviews/restaurants/{restaurantId}")
    public List<ReviewResponse> getAllTheReviewsOfTheRestaurant(@PathVariable Long restaurantId){
        return reviewService.getAllTheReviewsOfTheRestaurant(restaurantId);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(RestaurantNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<String> handleMenuNotFoundException(MenuNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND );
    }
}
