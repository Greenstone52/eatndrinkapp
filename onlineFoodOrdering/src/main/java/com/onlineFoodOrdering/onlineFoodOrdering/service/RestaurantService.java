package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.RestaurantAlreadyExistsException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.RestaurantIncorrectPasswordException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.RestaurantNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.UniqueTaxNumberException;
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

        boolean isExist = false;

        if(restaurantRepository.findRestaurantByNameAndProvinceAndDistrict(request.getName(),request.getProvince(),request.getDistrict()) == null ){
            restaurant.setPassword(request.getPassword());
            restaurant.setName(request.getName());
            restaurant.setType(request.getType());
            restaurant.setDistrict(request.getDistrict());
            restaurant.setProvince(request.getProvince());
            restaurant.setTaxNo(request.getTaxNo());

            try {
                restaurantRepository.save(restaurant);
            }catch (RuntimeException exception){
                throw new UniqueTaxNumberException("Incorrect tax number.");
            }

        }else{
            throw new RestaurantAlreadyExistsException("The restaurant has already exists.");
        }

    }

    public String changePasswordOfRestaurant(Long id, RestaurantPasswordUpdateRequest request){
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(()-> new RestaurantNotFoundException("There is no such a restaurant."));

        if(!restaurant.getPassword().equals(request.getOldPassword())){
            throw new RestaurantIncorrectPasswordException("Incorrect password.");
        } else if (restaurant.getPassword().equals(request.getNewPassword())) {
            throw new RestaurantIncorrectPasswordException("New password should not be same with beforehand.");
        } else if(!request.getNewPassword().equals(request.getNewPassword2())){
            throw new RestaurantIncorrectPasswordException("The repetitive password is not match with the new password.");
        }else{
            restaurant.setPassword(request.getNewPassword());
            restaurantRepository.save(restaurant);
            return "The password is updated successfully.";
        }

    }

    //Post kullanılacak
    public String deleteRestaurant(Long restaurantId, RestaurantDeleteRequest request){

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException("There is no such a restaurant."));

        if(restaurantRepository.findById(restaurantId).get().getPassword().equals(request.getPassword())){
            restaurantRepository.deleteById(restaurantId);
            return "The restaurant delete completely from the system.";
        }else{
            throw new RestaurantIncorrectPasswordException("Incorrect password.");
        }

    }

    public void updateRestaurantInfo(Long restaurantId, RestaurantUpdateRequest request){

        Restaurant updatingRestaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException("There is no such a restaurant."));

        if(request.getPassword().equals(updatingRestaurant.getPassword())){

            if(restaurantRepository.findRestaurantByNameAndProvinceAndDistrict(request.getName(),request.getProvince(),request.getDistrict()) == null){
                updatingRestaurant.setPassword(request.getPassword());
                updatingRestaurant.setName(request.getName());
                updatingRestaurant.setType(request.getType());
                updatingRestaurant.setDistrict(request.getDistrict());
                updatingRestaurant.setProvince(request.getProvince());
                updatingRestaurant.setTaxNo(request.getTaxNo());

                try {
                    restaurantRepository.save(updatingRestaurant);
                }catch (RuntimeException exception){
                    throw new UniqueTaxNumberException("Incorrect tax number.");
                }

            }else{
                throw new RestaurantAlreadyExistsException("The restaurant has already exists.");
            }

        }else{
            throw new RestaurantIncorrectPasswordException("Incorrect password.");
        }

    }

    public RestaurantPrivateInfoResponse getRestaurantPrivateInfo(Long restaurantId,RestaurantDeleteRequest request){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException("There is no such a restaurant."));

        if(request.getPassword().equals(restaurant.getPassword())){
            RestaurantPrivateInfoResponse response = new RestaurantPrivateInfoResponse(restaurant);
            return response;
        }else{
            throw new RestaurantIncorrectPasswordException("The password written is incorrect.");
        }
    }

    public RestaurantInfoResponse getRestaurantInfo(Long restaurantId){

        try {
            Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
            RestaurantInfoResponse response = new RestaurantInfoResponse(restaurant);
            return response;
        }catch(Exception exception){
            throw new RestaurantNotFoundException("There is no such a restaurant.");
        }
    }

    public String getTotalRestaurantIncome(Long restaurantId, RestaurantDeleteRequest request){

        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException("There is no such a restaurant."));

        if(request.getPassword().equals(restaurant.getPassword())){
            return restaurant.getName() + " has " + restaurant.getNetEndorsement() + " net income.";
        }else{
            throw new RestaurantIncorrectPasswordException("Incorrect password.");
        }


    }

    public String getTotalRestaurantProfit(Long restaurantId, RestaurantDeleteRequest request){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("There is no such a restaurant."));

        if(request.getPassword().equals(restaurant.getPassword())){
            return restaurant.getName() + " has " + restaurant.getNetProfit() + " net profit.";
        }else{
            return "Incorrect password";
        }
    }


}
