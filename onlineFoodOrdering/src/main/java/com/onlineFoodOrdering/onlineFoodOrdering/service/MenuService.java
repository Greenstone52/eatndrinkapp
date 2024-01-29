package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.MenuRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.MenuCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.MenuUpdateRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private RestaurantRepository restaurantRepository;
    private MenuRepository menuRepository;

    public void addMenu(Long restaurantId, MenuCreateRequest request){
        Menu menu = new Menu();
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);

        menu.setName(request.getName());
        menu.setRestaurant(restaurant);
    }

    public void updateMenu(Long menuId, MenuUpdateRequest request){
        Menu menu = menuRepository.findById(menuId).orElse(null);
        menu.setName(request.getName());
        menuRepository.save(menu);
    }

    public List<Menu> getAllTheMenuOfTheRestaurant(Long restaurantId){
        List<Menu> menus = new ArrayList<>();

        for (Long i = 0L; i < menuRepository.count(); i++) {
            if(menuRepository.findById(i).get().getRestaurant().equals(restaurantRepository.findById(restaurantId).orElse(null))){
                menus.add(menuRepository.findById(i).get());
            }
        }

        return menus;
    }

    public void deleteMenu(Long menuId){
        menuRepository.deleteById(menuId);
    }

}
