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

    public List<FoodDrink> getAllTheFoodDrinksOfTheRestaurant(Long restaurantId){
        List<FoodDrink> foodDrinkList = new ArrayList<>();
        List<Menu> menuList = new ArrayList<>();

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);

        // Finding the menus of the specified restaurant
        for (Long i = 0L; i < menuRepository.count(); i++) {
            if(menuRepository.findById(i).get().getRestaurant().equals(restaurant)){
                menuList.add(menuRepository.findById(i).get());
            }
        }

        int count = 0;
        // Finding the foodDrink of the menus
        for (Long i = 0L; i < menuList.size(); i++) {
            Menu menu = menuList.get(count);

            for (Long j = 0L; j < foodDrinkRepository.count(); j++) {
                if(foodDrinkRepository.findById(j).get().getMenu().equals(menu)){
                    foodDrinkList.add(foodDrinkRepository.findById(j).get());
                }
            }

            count++;
        }

        return foodDrinkList;
    }

    public List<FoodDrink> getOneMenusFoodDrink(Long menuId){
        List<FoodDrink> foodDrinkList = new ArrayList<>();

        for (Long i = 0L; i < foodDrinkRepository.count(); i++) {
            if (foodDrinkRepository.findById(i).get().getMenu().equals(menuRepository.findById(menuId))) {
                foodDrinkList.add(foodDrinkRepository.findById(i).get());
            }
        }

        return foodDrinkList;
    }

    public void addFoodDrink(Long menuId, FoodDrinkCreateRequest request){
        FoodDrink foodDrink = new FoodDrink();

        Menu menu = menuRepository.findById(menuId).orElse(null);
        foodDrink.setMenu(menu);
        foodDrink.setName(request.getName());
        foodDrink.setCostPrice(request.getCostPrice());
        foodDrink.setSalesPrice(request.getSalesPrice());

        foodDrinkRepository.save(foodDrink);
    }

    public void updateFoodDrink(Long foodDrinkId,FoodDrinkUpdateRequest request){

        FoodDrink foodDrink = foodDrinkRepository.findById(foodDrinkId).orElse(null);
        foodDrink.setSalesPrice(request.getSalesPrice());
        foodDrink.setCostPrice(request.getCostPrice());
        foodDrink.setName(request.getName());

        foodDrinkRepository.save(foodDrink);
    }

    public void deleteFoodDrink(Long foodDrinkId){
        foodDrinkRepository.deleteById(foodDrinkId);
    }
}
