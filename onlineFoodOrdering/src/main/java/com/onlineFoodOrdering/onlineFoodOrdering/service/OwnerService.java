package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.request.*;
import com.onlineFoodOrdering.onlineFoodOrdering.compositeKey.ShareRatioKey;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.*;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.*;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OwnerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.security.auth.AuthenticationRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.security.enums.Role;
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
    private UserRepository userRepository;
    private DetailsOfUserRepository detailsOfUserRepository;

    public List<OwnerResponse> getAllOwners(){
        List<Owner> owners = ownerRepository.findAll();
        List<OwnerResponse> result = owners.stream().map(owner->new OwnerResponse(owner)).collect(Collectors.toList());

        for (int i = 0; i < result.size(); i++) {
            Owner owner = ownerRepository.findById(owners.get(i).getId()).orElse(null);
            List<ShareRatio> shareRatio = shareRatioRepository.findShareRatioByOwnerId(owners.get(i).getId());
            List<RestaurantShareRatio> restaurantShareRatios = new ArrayList<>();

            for (int j = 0; j < shareRatio.size(); j++) {
                RestaurantShareRatio shareRatio1 = new RestaurantShareRatio();
                shareRatio1.setShareRatio(shareRatio.get(j).getShareRatio());
                shareRatio1.setRestaurantName(shareRatio.get(j).getRestaurant().getName());
                restaurantShareRatios.add(shareRatio1);
            }
            result.get(i).setResRatios(restaurantShareRatios);
        }

        return result;
    }

    public List<OwnerResponseWithoutSRRequest> getOwnersByRestaurantId(Long restaurantId){
        List<ShareRatio> shareRatios = shareRatioRepository.findShareRatioByRestaurantId(restaurantId);
        List<Owner> result = new ArrayList<>();

        for (int i = 0; i < shareRatios.size(); i++) {
            result.add(shareRatios.get(i).getOwner());
        }

        return result.stream().map(owner->new OwnerResponseWithoutSRRequest(owner)).collect(Collectors.toList());
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

    public List<OwnerResponse> getTopNOwners(Long N){

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

    //public void addOneOwner(OwnerUserCreateRequest request){
    //    Owner newOwner = new Owner();
    //    User user = userRepository.findUserByUserDetailsIdAndRole(newOwner.getId(), Role.OWNER);
    //    DetailsOfUser detailsOfUser = new DetailsOfUser();
//
    //    //newOwner.setBankAccount(owner.getBankAccount());
    //    //newOwner.setRestaurants(owner.getRestaurants());
//
    //    user.setEmail(request.getEmail());
    //    user.setPassword(request.getPassword());
    //    user.setRole(request.getRole());
    //    user.setUserDetailsId(newOwner.getId());
//
    //    //newOwner.setUsername(owner.getUsername());
    //    detailsOfUser.setGsm(request.getGsm());
    //    detailsOfUser.setGender(request.getGender());
    //    detailsOfUser.setLastName(request.getLastName());
    //    detailsOfUser.setFirstName(request.getFirstName());
    //    detailsOfUser.setBirthDate(request.getBirthDate());
//
    //    newOwner.setDetailsOfUser(detailsOfUser);
    //    newOwner.setBalance(0);
    //    newOwner.setUser(user);
//
    //    detailsOfUserRepository.save(detailsOfUser);
    //    userRepository.save(user);
    //    ownerRepository.save(newOwner);
    //}

    public void addOneOwner(AuthenticationRequest request){
        Owner newOwner = new Owner();
        User user = new User();
        DetailsOfUser detailsOfUser = new DetailsOfUser();

        //newOwner.setBankAccount(owner.getBankAccount());
        //newOwner.setRestaurants(owner.getRestaurants());

        Long userCount = userRepository.count();

        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());
        // user.setUserDetailsId(ownerRepository.count() + 1);
        userRepository.save(user);


        if(userRepository.count() >= userCount){
            //newOwner.setUsername(owner.getUsername());
            detailsOfUser.setGsm(request.getGsm());
            detailsOfUser.setGender(request.getGender());
            detailsOfUser.setLastName(request.getLastName());
            detailsOfUser.setFirstName(request.getFirstName());
            detailsOfUser.setBirthDate(request.getBirthDate());
            detailsOfUserRepository.save(detailsOfUser);
            newOwner.setDetailsOfUser(detailsOfUser);
        }

        newOwner.setBalance(0);
        newOwner.setUser(user);
        ownerRepository.save(newOwner);

        List<Owner> owners = ownerRepository.findAll();
        Owner lastOwner = owners.get(owners.size()-1);

        user.setUserDetailsId(lastOwner.getId());
        userRepository.save(user);
        newOwner.setUser(user);
        ownerRepository.save(newOwner);
    }

    public String setAnOwnerToARestaurant(Long ownerId, Long restaurantId, SetOwnerToARestaurantRequest request){
        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        List<ShareRatio> shareRatioList = shareRatioRepository.findShareRatioByRestaurantId(restaurantId);
        User user = userRepository.findUserByUserDetailsIdAndRole(owner.getId(),Role.OWNER);

        if(user.getPassword().equals(request.getOwnerPassword()) && restaurant.getPassword().equals(request.getRestaurantPassword())){

            boolean isAlreadyPartner = false;

            for (int i = 0; i < shareRatioList.size(); i++) {
                if(shareRatioList.get(i).getOwner() == owner){
                    isAlreadyPartner = true;
                    return "The owner was already an partner of this restaurant.";
                }
            }

            if(!isAlreadyPartner){
                //restaurant.getOwners().add(owner);
                ShareRatioKey sRKey = new ShareRatioKey(ownerId,restaurantId);
                ShareRatio shareRatio = new ShareRatio(sRKey,owner,restaurant,request.getShareRatio());
                shareRatioRepository.save(shareRatio);
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

    public String updateOneOwner(UserUpdateRequest request, Long id){
        Owner owner = ownerRepository.findById(id).orElse(null);
        User user = userRepository.findUserByUserDetailsIdAndRole(owner.getId(),Role.OWNER);

        if(owner != null){

            // Password will be decoded here before go to next lines.

            if(user.getPassword().equals(request.getPassword())){
                owner.getDetailsOfUser().setGender(request.getGender());
                owner.getDetailsOfUser().setGsm(request.getGsm());
                owner.getDetailsOfUser().setLastName(request.getLastName());
                owner.getDetailsOfUser().setFirstName(request.getLastName());
                owner.getDetailsOfUser().setBirthDate(request.getBirthDate());
                detailsOfUserRepository.save(owner.getDetailsOfUser());

                return "The details of the owner was updated successfully.";
            }

            return "The password entered is wrong.";

        }else{
            return "There is no such an owner in the system.";
        }
    }

    public String deleteOneOwner(Long id,OwnerDeleteRequest request){
        Owner owner = ownerRepository.findById(id).orElse(null);
        User user = userRepository.findUserByUserDetailsIdAndRole(owner.getId(),Role.OWNER);

        if(owner != null){

            // Password will be decoded here before go to next lines.

            if(user.getPassword().equals(request.getPassword())){
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