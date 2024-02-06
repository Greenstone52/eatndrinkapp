package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.MenuRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OwnerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestauranCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantInfoResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantPrivateInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RestaurantService {
    private RestaurantRepository restaurantRepository;

    public void addOneRestaurant(RestauranCreateRequest request){
        Restaurant restaurant = new Restaurant();

        //It will be saved as encrypted form in the database

        if(!restaurantRepository.existsRestaurantByName(request.getName())){
            restaurant.setPassword(request.getPassword());

            restaurant.setName(request.getName());
            restaurant.setDistrict(request.getDistrict());
            restaurant.setProvince(request.getProvince());
            restaurant.setTaxNo(request.getTaxNo());

            restaurantRepository.save(restaurant);
        }

    }

    public void setOwnerToRestaurant(Long ownerId,Long restaurantId){

    }

    public void changePasswordOfRestaurant(){

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

    public void updateRestaurantInfo(Long restaurantId, RestaurantUpdateRequest request){

        Restaurant updatingRestaurant = restaurantRepository.findById(restaurantId).orElse(null);

        updatingRestaurant.setName(request.getName());
        updatingRestaurant.setProvince(request.getProvince());
        updatingRestaurant.setDistrict(request.getDistrict());
        updatingRestaurant.setTaxNo(request.getTaxNo());

        restaurantRepository.save(updatingRestaurant);
    }

    public RestaurantPrivateInfoResponse getRestaurantPrivateInfo(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        RestaurantPrivateInfoResponse response = new RestaurantPrivateInfoResponse(restaurant);
        return response;
    }

    public RestaurantInfoResponse getRestaurantInfo(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        RestaurantInfoResponse response = new RestaurantInfoResponse(restaurant);
        return response;
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
