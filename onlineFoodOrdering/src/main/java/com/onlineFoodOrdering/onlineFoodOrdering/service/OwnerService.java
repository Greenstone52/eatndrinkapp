package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Owner;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.ShareRatio;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OwnerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.ShareRatioRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.SetOwnerToARestaurantRequest;
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
    private ShareRatioRepository shareRatioRepository;
    public List<OwnerResponse> getAllOwners(){
        List<Owner> owners = ownerRepository.findAll();
        return owners.stream().map(owner->new OwnerResponse(owner)).collect(Collectors.toList());
    }

    public List<OwnerResponse> getOwnersByRestaurantId(Long restaurantId){
        List<ShareRatio> shareRatios = shareRatioRepository.findShareRatioByRestaurantId(restaurantId);
        List<Owner> result = new ArrayList<>();

        for (int i = 0; i < shareRatios.size(); i++) {
            result.add(shareRatios.get(i).getOwner());
        }

        return result.stream().map(owner->new OwnerResponse(owner)).collect(Collectors.toList());
    }

    public List<OwnerResponse> getTopFiveOwners(){
        return getOwners(5L);
    }

    public List<OwnerResponse> getTop10Owners(){
        return getOwners(10L);
    }

    public List<OwnerResponse> getOwners(Long N){
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
        int index = -1;

        for (int j = 0; j < N; j++) {
            for (int i = 0; i < ownerRepository.count(); i++) {
                if(allOwners.get(i).getBalance() > maxVal){
                    maxVal = allOwners.get(i).getBalance();
                    index = i;
                }
            }

            topN.add(allOwners.get(index));
            allOwners.remove(index);
            index = -1;
        }

        return topN.stream().map(owner -> new OwnerResponse(owner)).collect(Collectors.toList());

    }

    public void addOneOwner(Owner owner){
        Owner newOwner = new Owner();

        //newOwner.setBankAccount(owner.getBankAccount());
        //newOwner.setRestaurants(owner.getRestaurants());

        newOwner.setEmail(owner.getEmail());
        newOwner.setPassword(owner.getPassword());

        //newOwner.setUsername(owner.getUsername());

        newOwner.setDetailsOfUser(owner.getDetailsOfUser());
        newOwner.setBalance(owner.getBalance());
        newOwner.setBirthDate(owner.getBirthDate());

        ownerRepository.save(newOwner);
    }

    public String setAnOwnerToARestaurant(Long ownerId, Long restaurantId, SetOwnerToARestaurantRequest request){
        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        ShareRatio shareRatio = new ShareRatio();
        List<ShareRatio> shareRatioList = shareRatioRepository.findShareRatioByRestaurantId(restaurantId);


        if(owner.getPassword().equals(request.getOwnerPassword()) && restaurant.getPassword().equals(request.getRestaurantPassword())){

            boolean isAlreadyPartner = false;

            for (int i = 0; i < shareRatioList.size(); i++) {
                if(shareRatioList.get(i).getOwner() == owner){
                    isAlreadyPartner = true;
                    return "The owner was already an partner of this restaurant.";
                }
            }

            if(!isAlreadyPartner){
                //restaurant.getOwners().add(owner);
                shareRatio.setRestaurant(restaurant);
                shareRatio.setOwner(owner);
                return "The process was completed successfully.";
            }

            //for (int i = 0; i < restaurant.getOwners().size(); i++) {
            //    if(restaurant.getOwners().get(i) == owner){
            //        isAlreadyPartner = true;
            //        return "The owner was already an partner of this restaurant.";
            //    }
            //}
//
            //if(!isAlreadyPartner){
            //    restaurant.getOwners().add(owner);
            //    return "The process was completed successfully.";
            //}
        }

        return "Incorrent information!";

    }

    public String updateOneOwner(OwnerUpdateRequest request, Long id){
        Owner owner = ownerRepository.findById(id).orElse(null);

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

            return "The password entered is wrong.";

        }else{
            return "There is no such an owner in the system.";
        }
    }

    public String deleteOneOwner(Long id,OwnerDeleteRequest request){
        Owner owner = ownerRepository.findById(id).orElse(null);

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

    public List<OwnerResponse> getTopNMostEarnedOwners(int n){
        List<Owner> owners = ownerRepository.findAll();
        List<Owner> result = new ArrayList<>();

        double min = 0;
        int index = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < owners.size(); j++) {
                if(owners.get(j).getBalance()>min){
                    min = owners.get(j).getBalance();
                    index = j;
                }
            }
            result.add(owners.get(index));
            owners.remove(index);
            index = -1;
        }

        return result.stream().map(owner -> new OwnerResponse(owner)).collect(Collectors.toList());
    }

    public List<OwnerResponse> getTop5MostEarnedOwners(){
        return getTopNMostEarnedOwners(5);
    }

    public List<OwnerResponse> getTop10MostEarnedOwners(){
        return getTopNMostEarnedOwners(10);
    }

}
