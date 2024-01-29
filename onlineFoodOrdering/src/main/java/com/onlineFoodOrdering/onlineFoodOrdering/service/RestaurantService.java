package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.MenuRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OwnerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestauranCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantDeleteRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantService {
    private RestaurantRepository restaurantRepository;
    private MenuRepository menuRepository;
    private FoodDrinkRepository foodDrinkRepository;
    private OwnerRepository ownerRepository;

    public void addOneRestaurant(RestauranCreateRequest request){
        Restaurant restaurant = new Restaurant();
        restaurant.setBalance(0);

        //It will be saved as encrypted form in the database
        restaurant.setPassword(restaurant.getPassword());
        restaurant.setName(request.getName());
        restaurant.setDistrict(request.getDistrict());
        restaurant.setProvince(request.getProvince());
        restaurant.setTaxNo(request.getTaxNo());

        restaurantRepository.save(restaurant);
    }

    public void setOwnerToRestaurant(){

    }

    //Post kullanılacak
    public String deleteRestaurant(Long restaurantId, RestaurantDeleteRequest request){

        if(restaurantRepository.findById(restaurantId).get().getPassword().equals(request.getPassword())){
            restaurantRepository.deleteById(restaurantId);
            return "The restaurant delete completely from the system.";
        }else{
            return "You entered incorrect password";
        }

    }

    public void updateRestaurantInfo(){

    }

    public void getRestaurantInfo(){

    }

    public double getTotalRestaurantIncome(Long restaurantId, RestaurantDeleteRequest request){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        return restaurant.getNetEndorsement();
    }

    public double getTotalRestaurantProfit(Long restaurantId, RestaurantDeleteRequest request){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();
        return restaurant.getNetProfit();
    }


}
