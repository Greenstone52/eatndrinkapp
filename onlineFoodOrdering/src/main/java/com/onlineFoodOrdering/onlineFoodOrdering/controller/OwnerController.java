package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.*;
import com.onlineFoodOrdering.onlineFoodOrdering.request.*;
import com.onlineFoodOrdering.onlineFoodOrdering.response.MenuWithFoodDrinkResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OrderResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantPrivateInfoResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public FoodDrink addFoodDrink(@PathVariable Long menuId, @RequestBody FoodDrinkCreateRequest request) {
        return foodDrinkService.addFoodDrink(menuId,request);
    }

    @PutMapping("/foodDrinks/{foodDrinkId}")
    public void updateFoodDrink(@PathVariable Long foodDrinkId, @RequestBody FoodDrinkCreateRequest request){
        foodDrinkService.updateFoodDrink(foodDrinkId,request);
    }

    @PostMapping("/foodDrinks/delete/{foodDrinkId}")
    public String deleteFoodDrink(@PathVariable Long foodDrinkId, @RequestBody RestaurantDeleteRequest request){
        return foodDrinkService.deleteFoodDrink(foodDrinkId, request);
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

    @PostMapping("/menus/delete/{menuId}")
    public String deleteMenu(@PathVariable Long menuId, @RequestBody RestaurantDeleteRequest request){
        return menuService.deleteMenu(menuId,request);
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

    @DeleteMapping("/owners/{id}")
    public String deleteOneOwner(@PathVariable Long id){
        return ownerService.deleteOneOwner(id);
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
    public RestaurantPrivateInfoResponse getRestaurantPrivateInfo(@PathVariable Long restaurantId, @RequestBody RestaurantDeleteRequest request){
        return restaurantService.getRestaurantPrivateInfo(restaurantId,request);
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

    @ExceptionHandler(RestaurantIncorrectPasswordException.class)
    public ResponseEntity<String> handleRestaurantIncorrectPasswordException(RestaurantIncorrectPasswordException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<String> handleMenuNotFoundException(MenuNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MenuAlreadyExistsException.class)
    public ResponseEntity<String> handleMenuAlreadyExistsException(MenuAlreadyExistsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OwnerNotFoundException.class)
    public ResponseEntity<String> handleOwnerNotFoundException(OwnerNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(RestaurantNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FoodDrinkNotFoundException.class)
    public ResponseEntity<String> handleFoodDrinkNotFoundException(FoodDrinkNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantAlreadyExistsException.class)
    public ResponseEntity<String> handleRestaurantAlreadyExistsException(RestaurantAlreadyExistsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }
}
