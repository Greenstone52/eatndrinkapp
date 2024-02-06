package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.request.RestauranCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantInfoResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantPrivateInfoResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {
    private RestaurantService restaurantService;

    @PostMapping
    public void addOneRestaurant(@RequestBody RestauranCreateRequest request) {
        restaurantService.addOneRestaurant(request);
    }

    @DeleteMapping("/{restaurantId}")
    public String deleteRestaurant(@PathVariable Long restaurantId, @RequestBody RestaurantDeleteRequest request){
        return restaurantService.deleteRestaurant(restaurantId,request);
    }

    @PutMapping("/{restaurantId}")
    public void updateRestaurantInfo(@PathVariable Long restaurantId, @RequestBody RestaurantUpdateRequest request){
        restaurantService.updateRestaurantInfo(restaurantId,request);
    }

    @GetMapping("/{restaurantId}/privateInfo")
    public RestaurantPrivateInfoResponse getRestaurantPrivateInfo(@PathVariable Long restaurantId){
        return restaurantService.getRestaurantPrivateInfo(restaurantId);
    }

    @GetMapping("/{restaurantId}/info")
    public RestaurantInfoResponse getRestaurantInfo(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurantInfo(restaurantId);
    }

    @GetMapping("/{restaurantId}/income")
    public double getTotalRestaurantIncome(@PathVariable Long restaurantId, @RequestBody RestaurantDeleteRequest request){
        return restaurantService.getTotalRestaurantIncome(restaurantId,request);
    }

    @GetMapping("/{restaurantId}/profit")
    public double getTotalRestaurantProfit(@PathVariable Long restaurantId, @RequestBody RestaurantDeleteRequest request){
        return restaurantService.getTotalRestaurantProfit(restaurantId,request);
    }

}
