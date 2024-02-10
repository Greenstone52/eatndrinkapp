package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.MenuRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OwnerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestauranCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantPasswordUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.RestaurantUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantInfoResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantPrivateInfoResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.RestaurantResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantService {
    private RestaurantRepository restaurantRepository;

    public List<RestaurantResponse> getAllTheRestaurants(@RequestParam(required = false) String type){

        if(type != null){
            return restaurantRepository.findRestaurantByType(type).stream().map(restaurant -> new RestaurantResponse(restaurant))
                    .collect(Collectors.toList());
        }else{
            return restaurantRepository.findAll().stream().map(restaurant -> new RestaurantResponse(restaurant))
                    .collect(Collectors.toList());
        }
    }

    public void addOneRestaurant(RestauranCreateRequest request){
        Restaurant restaurant = new Restaurant();



        if(!restaurantRepository.existsRestaurantByName(request.getName())){
            //It will be saved as encrypted form in the database
            restaurant.setPassword(request.getPassword());

            restaurant.setName(request.getName());
            restaurant.setType(request.getType());
            restaurant.setDistrict(request.getDistrict());
            restaurant.setProvince(request.getProvince());
            restaurant.setTaxNo(request.getTaxNo());

            restaurantRepository.save(restaurant);
        }

    }

    public void setOwnerToRestaurant(Long ownerId,Long restaurantId){

    }

    public String changePasswordOfRestaurant(Long id, RestaurantPasswordUpdateRequest request){
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

        if(!restaurant.getPassword().equals(request.getOldPassword())){
            return "Incorrect password";
        } else if (restaurant.getPassword().equals(request.getNewPassword())) {
            return "New password should not be same with beforehand.";
        } else if(!request.getNewPassword().equals(request.getNewPassword2())){
            return "The repetitive password is not match with the new password.";
        }else{
            restaurant.setPassword(request.getNewPassword());
            restaurantRepository.save(restaurant);
            return "The password is updated successfully.";
        }

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
        updatingRestaurant.setType(request.getType());

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

    public String getTotalRestaurantIncome(Long restaurantId, RestaurantDeleteRequest request){

        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        if(request.getPassword().equals(restaurant.getPassword())){
            return restaurant.getName() + " has " + restaurant.getNetEndorsement() + " net income.";
        }else{
            return "Incorrect password";
        }


    }

    public String getTotalRestaurantProfit(Long restaurantId, RestaurantDeleteRequest request){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).get();

        if(request.getPassword().equals(restaurant.getPassword())){
            return restaurant.getName() + " has " + restaurant.getNetProfit() + " net profit.";
        }else{
            return "Incorrect password";
        }
    }


}
