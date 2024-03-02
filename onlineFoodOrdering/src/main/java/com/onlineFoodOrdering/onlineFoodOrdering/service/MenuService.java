package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.MenuAlreadyExistsException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.MenuNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.RestaurantIncorrectPasswordException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.RestaurantNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.MenuRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.MenuCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.MenuUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.FoodDrinkResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.MenuWithFoodDrinkResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.MenuWithFoodDrinkResponseForCustomer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuService {

    private RestaurantRepository restaurantRepository;
    private MenuRepository menuRepository;
    private FoodDrinkRepository foodDrinkRepository;

    public String addMenu(Long restaurantId, MenuCreateRequest request){
        Menu menu = menuRepository.findMenuByNameAndRestaurantId(request.getName(),restaurantId).orElse(null);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException("There is no such a restaurant."));

        if(menu == null){
            menu = new Menu();
            menu.setName(request.getName());
            menu.setRestaurant(restaurant);
            menuRepository.save(menu);
            return "The menu is saved successfully.";
        }else{
            throw new MenuAlreadyExistsException("The menu is already exists.");
        }
    }

    public void updateMenu(Long menuId, MenuUpdateRequest request){
        Menu menu = menuRepository.findById(menuId).orElseThrow(()-> new MenuNotFoundException("There is no such a menu"));
        menu.setName(request.getName());
        menuRepository.save(menu);
    }

    public List<MenuWithFoodDrinkResponse> getAllTheMenuOfTheRestaurant(Long restaurantId){

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("There is no such a restaurant."));

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

    public List<MenuWithFoodDrinkResponseForCustomer> getAllTheMenuOfTheRestaurantForCustomers(Long restaurantId){

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);

        if(restaurant == null){
            throw new RestaurantNotFoundException("There is no such a restaurant.");
        }else{
            List<Menu> menus = menuRepository.findMenuByRestaurantId(restaurantId);
            List<MenuWithFoodDrinkResponseForCustomer> responseList = new ArrayList<>();

            for (int i = 0; i < menus.size(); i++) {
                MenuWithFoodDrinkResponseForCustomer response = new MenuWithFoodDrinkResponseForCustomer(menus.get(i));
                List<FoodDrink> foodDrinkList = foodDrinkRepository.findFoodDrinkByMenuId(menus.get(i).getId());

                response.setFoodDrinkListResponse(foodDrinkList.stream().map(foodDrinkResponse -> new FoodDrinkResponse(foodDrinkResponse)).collect(Collectors.toList()));
                responseList.add(response);
            }

            return responseList;
        }
    }

    public String deleteMenu(Long menuId, RestaurantDeleteRequest request) {

        Menu menu = menuRepository.findById(menuId).orElseThrow(()-> new MenuNotFoundException("There is no such a menu."));

            Restaurant restaurant = menu.getRestaurant();

            if(restaurant.getPassword().equals(request.getPassword())){
                menuRepository.deleteById(menuId);
                return "The menu was removed from the system successfully.";
            }else{
                throw new RestaurantIncorrectPasswordException("The password entered is incorrect.");
            }
    }
}
