package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Owner;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OwnerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OrderResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OwnerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OwnerService {

    private OwnerRepository ownerRepository;
    private RestaurantRepository restaurantRepository;
    public List<OwnerResponse> getAllOwners(){
        List<Owner> owners = ownerRepository.findAll();
        return owners.stream().map(owner->new OwnerResponse(owner)).collect(Collectors.toList());
    }

    public List<OwnerResponse> getOwnersByRestaurantId(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        List<Owner> owners = restaurant.getOwners();
        return owners.stream().map(owner->new OwnerResponse(owner)).collect(Collectors.toList());
    }

    public List<OwnerResponse> getTopFiveOwners(){
        return getOwners(5);
    }

    public List<OwnerResponse> getTop10Owners(){
        return getOwners(10);
    }

    public List<OwnerResponse> getOwners(long N){
        if (ownerRepository.count() >= N){
            return getTopNOwners(N);
        }else{
            return getTopNOwners(ownerRepository.count());
        }
    }

    public List<OwnerResponse> getTopNOwners(long N){

        List<Owner> allOwners = ownerRepository.findAll();
        ArrayList<Owner> topN = new ArrayList<>();

        double maxVal = 0;
        int index = 0;

        for (int j = 0; j < N; j++) {
            for (int i = 0; i < ownerRepository.count(); i++) {
                if(allOwners.get(i).getBankAccount().getBalance() > maxVal){
                    maxVal = allOwners.get(i).getBankAccount().getBalance();
                    index = i;
                }
            }

            topN.add(allOwners.get(index));
            allOwners.remove(index);
            index = 0;
        }

        return topN.stream().map(owner -> new OwnerResponse(owner)).collect(Collectors.toList());

    }

    public void addOneOwner(Owner owner){
        Owner newOwner = new Owner();
        newOwner.setBankAccount(owner.getBankAccount());
        newOwner.setRestaurants(owner.getRestaurants());
        newOwner.setEmail(owner.getEmail());
        newOwner.setPassword(owner.getPassword());
        newOwner.setUsername(owner.getUsername());
        newOwner.setDetailsOfUser(owner.getDetailsOfUser());
        ownerRepository.save(newOwner);
    }

    public String updateOneOwner(OwnerUpdateRequest request){
        Owner owner = ownerRepository.findOwnerByUsername(request.getUsername()).orElse(null);

        if(owner != null){

            // Password will be decoded here before go to next lines.

            if(owner.getPassword().equals(request.getPassword())){
                owner.getDetailsOfUser().setGender(request.getGender());
                owner.getDetailsOfUser().setGsm(request.getGsm());
                owner.getDetailsOfUser().setLastName(request.getLastname());
                owner.getDetailsOfUser().setFirstName(request.getFirstname());
                owner.getDetailsOfUser().setBirthDate(request.getBirthDate());
                return "The details of the owner was updated successfully.";
            }

            return "The password entered is wrong";

        }else{
            return "There is no such an owner in the system.";
        }
    }

    public String deleteOneOwner(OwnerDeleteRequest request){
        Owner owner = ownerRepository.findOwnerByUsername(request.getUsername()).orElse(null);

        if(owner != null){

            // Password will be decoded here before go to next lines.

            if(owner.getPassword().equals(request.getPassword())){
                ownerRepository.delete(owner);
                return "The owner is removed from the system.";
            }

            return "The password entered is wrong";

        }else{
            return "There is no such an owner in the system.";
        }
    }


}
