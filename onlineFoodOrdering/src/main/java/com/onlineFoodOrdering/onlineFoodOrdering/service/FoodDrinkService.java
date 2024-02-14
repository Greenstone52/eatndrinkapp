package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.MenuRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.FoodDrinkCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.FoodDrinkUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FoodDrinkService {

    private FoodDrinkRepository foodDrinkRepository;
    private MenuRepository menuRepository;
    private RestaurantRepository restaurantRepository;

    //public List<FoodDrink> getAllTheFoodDrinksOfTheRestaurant(Long restaurantId){
    //    List<FoodDrink> foodDrinkList = new ArrayList<>();
    //    List<Menu> menuList = new ArrayList<>();
//
    //    Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
//
    //    // Finding the menus of the specified restaurant
    //    for (Long i = 0L; i < menuRepository.count(); i++) {
    //        if(menuRepository.findById(i).get().getRestaurant().equals(restaurant)){
    //            menuList.add(menuRepository.findById(i).get());
    //        }
    //    }
//
    //    int count = 0;
    //    // Finding the foodDrink of the menus
    //    for (Long i = 0L; i < menuList.size(); i++) {
    //        Menu menu = menuList.get(count);
//
    //        for (Long j = 0L; j < foodDrinkRepository.count(); j++) {
    //            if(foodDrinkRepository.findById(j).get().getMenu().equals(menu)){
    //                foodDrinkList.add(foodDrinkRepository.findById(j).get());
    //            }
    //        }
//
    //        count++;
    //    }
//
    //    return foodDrinkList;
    //}

    public List<FoodDrink> getOneMenusFoodDrink(Long menuId){
        return foodDrinkRepository.findFoodDrinkByMenuId(menuId);
    }

    public String addFoodDrink(Long menuId, FoodDrinkCreateRequest request){


        Menu menu = menuRepository.findById(menuId).orElse(null);

        if(menu == null){
            return "There is no such a menu to add a food or drink.";
        }else{
            FoodDrink foodDrink = new FoodDrink(menu,request.getName(),request.getSalesPrice(),request.getCostPrice());
            foodDrinkRepository.save(foodDrink);
            return "The food/drink is added to system.";
        }

    }

    public void updateFoodDrink(Long foodDrinkId,FoodDrinkCreateRequest request){

        FoodDrink foodDrink = foodDrinkRepository.findById(foodDrinkId).orElse(null);
        foodDrink.setSalesPrice(request.getSalesPrice());
        foodDrink.setCostPrice(request.getCostPrice());
        foodDrink.setName(request.getName());
        foodDrink.setProfit(request.getSalesPrice()-request.getCostPrice());
        foodDrinkRepository.save(foodDrink);
    }

    public void deleteFoodDrink(Long foodDrinkId){
        foodDrinkRepository.deleteById(foodDrinkId);
    }
}
