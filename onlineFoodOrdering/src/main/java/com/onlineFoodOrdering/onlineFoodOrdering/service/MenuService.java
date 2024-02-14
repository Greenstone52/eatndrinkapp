package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.MenuRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.MenuCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.MenuUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.MenuWithFoodDrinkResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private RestaurantRepository restaurantRepository;
    private MenuRepository menuRepository;
    private FoodDrinkRepository foodDrinkRepository;

    public String addMenu(Long restaurantId, MenuCreateRequest request){
        Menu menu = menuRepository.findMenuByNameAndRestaurantId(request.getName(),restaurantId).orElse(null);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);

        if(menu == null){
            menu = new Menu();
            menu.setName(request.getName());
            menu.setRestaurant(restaurant);
            menuRepository.save(menu);
            return "The menu is saved successfully.";
        }else{
            return "The menu was already saved.";
        }
    }

    public void updateMenu(Long menuId, MenuUpdateRequest request){
        Menu menu = menuRepository.findById(menuId).orElse(null);
        menu.setName(request.getName());
        menuRepository.save(menu);
    }

    public List<MenuWithFoodDrinkResponse> getAllTheMenuOfTheRestaurant(Long restaurantId){
        
        List<Long> menuIDs = new ArrayList<>();
        List<Menu> menus = menuRepository.findMenuByRestaurantId(restaurantId);
        List<MenuWithFoodDrinkResponse> responseList = new ArrayList<>();

        for (int i = 0; i < menus.size(); i++) {
            MenuWithFoodDrinkResponse response = new MenuWithFoodDrinkResponse(menus.get(i));
            List<FoodDrink> foodDrinkList = foodDrinkRepository.findFoodDrinkByMenuId(menus.get(i).getId());
            response.setFoodDrinkList(foodDrinkList);
            responseList.add(response);
        }

        return responseList;
        //return menuRepository.findMenuByRestaurantId(restaurantId);
    }

    public void deleteMenu(Long menuId){
        menuRepository.deleteById(menuId);
    }

}
