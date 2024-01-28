package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.MenuRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OwnerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantService {
    private RestaurantRepository restaurantRepository;
    private MenuRepository menuRepository;
    private FoodDrinkRepository foodDrinkRepository;
    private OwnerRepository ownerRepository;

    public void addOneRestaurant(){

    }

    public void deleteRestaurant(){

    }

    public void updateRestaurantInfo(){

    }

    public void getRestaurantInfo(){

    }

    public void getRestaurantIncome(){

    }


}
