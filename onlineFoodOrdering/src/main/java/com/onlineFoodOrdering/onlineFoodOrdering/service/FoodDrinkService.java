package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.FoodDrinkNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.MenuNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.MenuRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.FoodDrinkCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantDeleteRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

        Menu menu = menuRepository.findById(menuId).orElse(null);

        if(menu == null){
            throw new MenuNotFoundException("There is no such a menu.");
        }else{
            return foodDrinkRepository.findFoodDrinkByMenuId(menuId);
        }
    }

    public FoodDrink addFoodDrink(Long menuId, FoodDrinkCreateRequest request){

        Menu menu = menuRepository.findById(menuId).orElseThrow(()-> new MenuNotFoundException("There is no such a menu."));

        FoodDrink foodDrink = new FoodDrink(menu,request.getName(),request.getSalesPrice(),request.getCostPrice());
        foodDrinkRepository.save(foodDrink);
        return foodDrink;
    }

    public void updateFoodDrink(Long foodDrinkId,FoodDrinkCreateRequest request){

        FoodDrink foodDrink = foodDrinkRepository.findById(foodDrinkId).orElseThrow(()-> new FoodDrinkNotFoundException("There is no such a food or a drink."));
        foodDrink.setSalesPrice(request.getSalesPrice());
        foodDrink.setCostPrice(request.getCostPrice());
        foodDrink.setName(request.getName());
        foodDrink.setProfit(request.getSalesPrice()-request.getCostPrice());
        foodDrinkRepository.save(foodDrink);
    }

    public String deleteFoodDrink(Long foodDrinkId, RestaurantDeleteRequest request){
        FoodDrink foodDrink = foodDrinkRepository.findById(foodDrinkId).orElseThrow(()->new FoodDrinkNotFoundException("There is no such a food or a drink."));
        Menu menu = foodDrink.getMenu();
        Restaurant restaurant = menu.getRestaurant();
        if(restaurant.getPassword().equals(request.getPassword())){
            String name = foodDrink.getName();
            foodDrinkRepository.deleteById(foodDrinkId);
            return "The food or drink called " + name + " was deleted from the system successfully.";
        }else{
            return "The password entered is incorrect";
        }
    }
}
