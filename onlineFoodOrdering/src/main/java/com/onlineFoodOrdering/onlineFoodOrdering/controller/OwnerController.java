package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.request.*;
import com.onlineFoodOrdering.onlineFoodOrdering.response.MenuWithFoodDrinkResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OrderResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantPrivateInfoResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
@AllArgsConstructor
public class OwnerController {

    private FoodDrinkService foodDrinkService;
    private MenuService menuService;
    private OrderService orderService;
    private OwnerService ownerService;
    private RestaurantService restaurantService;

    //FoodDrink
    @PostMapping("/foodDrinks/{menuId}")
    public String addFoodDrink(@PathVariable Long menuId, @RequestBody FoodDrinkCreateRequest request) {
        return foodDrinkService.addFoodDrink(menuId,request);
    }

    @PutMapping("/foodDrinks/{foodDrinkId}")
    public void updateFoodDrink(@PathVariable Long foodDrinkId, @RequestBody FoodDrinkCreateRequest request){
        foodDrinkService.updateFoodDrink(foodDrinkId,request);
    }

    @DeleteMapping("/foodDrinks/{foodDrinkId}")
    public void deleteFoodDrink(@PathVariable Long foodDrinkId){
        foodDrinkService.deleteFoodDrink(foodDrinkId);
    }

    // Menu
    @GetMapping("/menus/{restaurantId}/privates")
    public List<MenuWithFoodDrinkResponse> getAllTheMenuOfTheRestaurant(@PathVariable Long restaurantId){
        return menuService.getAllTheMenuOfTheRestaurant(restaurantId);
    }

    @PostMapping("/menus/{restaurantId}")
    public String addMenu(@PathVariable Long restaurantId, @RequestBody MenuCreateRequest request){
        return menuService.addMenu(restaurantId, request);
    }

    @PutMapping("/menus/{menuId}")
    public void updateMenu(@PathVariable Long menuId, @RequestBody MenuUpdateRequest request){
        menuService.updateMenu(menuId,request);
    }

    @DeleteMapping("/menus/{menuId}")
    public void deleteMenu(@PathVariable Long menuId){
        menuService.deleteMenu(menuId);
    }

    // Order
    @GetMapping("/orders/restaurants/{restaurantId}")
    public List<OrderResponse> getAllTheOrdersOfTheRestaurant(@PathVariable Long restaurantId){
        return orderService.getAllTheOrdersOfTheRestaurant(restaurantId);
    }

    // Owner
    @PostMapping("/owners/{ownerId}/restaurants/{restaurantId}")
    public String setAnOwnerToARestaurant(@PathVariable Long ownerId, @PathVariable Long restaurantId, @RequestBody SetOwnerToARestaurantRequest request){
        return ownerService.setAnOwnerToARestaurant(ownerId,restaurantId,request);
    }

    @PutMapping("/owners/{id}")
    public String updateOneOwner(@RequestBody UserUpdateRequest request, @PathVariable Long id){
        return ownerService.updateOneOwner(request,id);
    }

    @PostMapping("/owners/{id}")
    public String deleteOneOwner(@PathVariable Long id, @RequestBody OwnerDeleteRequest request){
        return ownerService.deleteOneOwner(id,request);
    }

    // Restaurant
    @PostMapping("/restaurants")
    public void addOneRestaurant(@RequestBody RestauranCreateRequest request) {
        restaurantService.addOneRestaurant(request);
    }

    @PostMapping("/restaurants/delete/{restaurantId}")
    public String deleteRestaurant(@PathVariable Long restaurantId, @RequestBody RestaurantDeleteRequest request){
        return restaurantService.deleteRestaurant(restaurantId,request);
    }

    @PutMapping("/restaurants/{restaurantId}")
    public void updateRestaurantInfo(@PathVariable Long restaurantId, @RequestBody RestaurantUpdateRequest request){
        restaurantService.updateRestaurantInfo(restaurantId,request);
    }

    @GetMapping("/restaurants/{restaurantId}/privateInfo")
    public RestaurantPrivateInfoResponse getRestaurantPrivateInfo(@PathVariable Long restaurantId){
        return restaurantService.getRestaurantPrivateInfo(restaurantId);
    }

    @GetMapping("/restaurants/{restaurantId}/income")
    public String getTotalRestaurantIncome(@PathVariable Long restaurantId, @RequestBody RestaurantDeleteRequest request){
        return restaurantService.getTotalRestaurantIncome(restaurantId,request);
    }

    @GetMapping("/restaurants/{restaurantId}/profit")
    public String getTotalRestaurantProfit(@PathVariable Long restaurantId, @RequestBody RestaurantDeleteRequest request){
        return restaurantService.getTotalRestaurantProfit(restaurantId,request);
    }

    @PostMapping("/restaurants/{restaurantId}/changePassword")
    public String changePasswordOfRestaurant(@PathVariable(value = "restaurantId") Long id, @RequestBody RestaurantPasswordUpdateRequest request){
        return restaurantService.changePasswordOfRestaurant(id,request);
    }
}
