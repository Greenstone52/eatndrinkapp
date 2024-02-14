package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import com.onlineFoodOrdering.onlineFoodOrdering.request.MenuCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.MenuUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.MenuWithFoodDrinkResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/menus")
public class MenuController {
    private MenuService menuService;

    @GetMapping("/{restaurantId}")
    public List<MenuWithFoodDrinkResponse> getAllTheMenuOfTheRestaurant(@PathVariable Long restaurantId){
        return menuService.getAllTheMenuOfTheRestaurant(restaurantId);
    }

    @PostMapping("/{restaurantId}")
    public String addMenu(@PathVariable Long restaurantId, @RequestBody MenuCreateRequest request){
        return menuService.addMenu(restaurantId, request);
    }

    @PutMapping("/{menuId}")
    public void updateMenu(@PathVariable Long menuId, @RequestBody MenuUpdateRequest request){
        menuService.updateMenu(menuId,request);
    }

    @DeleteMapping("/{menuId}")
    public void deleteMenu(@PathVariable Long menuId){
        menuService.deleteMenu(menuId);
    }
}
