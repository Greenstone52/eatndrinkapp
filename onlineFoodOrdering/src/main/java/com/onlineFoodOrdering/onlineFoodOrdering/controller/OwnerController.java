package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.ShareRatio;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerResponseWithoutSRRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.SetOwnerToARestaurantRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OwnerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.security.auth.AuthenticationRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/owners")
public class OwnerController {
    private OwnerService ownerService;

    @GetMapping
    public List<OwnerResponse> getAllOwners(){
        return ownerService.getAllOwners();
    }

    @GetMapping("/{restaurantId}")
    public List<OwnerResponseWithoutSRRequest> getOwnersByRestaurantId(@PathVariable Long restaurantId){
        return ownerService.getOwnersByRestaurantId(restaurantId);
    }

    @GetMapping("/top5")
    public List<OwnerResponse> getTopFiveOwners(){
        return ownerService.getTopFiveOwners();
    }

    @GetMapping("/top10")
    public List<OwnerResponse> getTop10Owners(){
        return ownerService.getTop10Owners();
    }

    @GetMapping("/topN")
    public List<OwnerResponse> getTopNOwners(@PathVariable Long topN){
        return ownerService.getTopNOwners(topN);
    }

    @PostMapping
    public void addOneOwner(@RequestBody AuthenticationRequest request){
        ownerService.addOneOwner(request);
    }

    @PostMapping("/{ownerId}/restaurants/{restaurantId}")
    public String setAnOwnerToARestaurant(@PathVariable Long ownerId, @PathVariable Long restaurantId, @RequestBody SetOwnerToARestaurantRequest request){
        return ownerService.setAnOwnerToARestaurant(ownerId,restaurantId,request);
    }

    @PutMapping("/{id}")
    public String updateOneOwner(@RequestBody OwnerUpdateRequest request, @PathVariable Long id){
        return ownerService.updateOneOwner(request,id);
    }

    @DeleteMapping("/{id}")
    public String deleteOneOwner(@PathVariable Long id, @RequestBody OwnerDeleteRequest request){
        return ownerService.deleteOneOwner(id,request);
    }

    @GetMapping("/mostEarn/{nMostEarn}")
    public List<OwnerResponse> getTopNMostEarnedOwners(@PathVariable int nMostEarn){
        return ownerService.getTopNMostEarnedOwners(nMostEarn);
    }

    @GetMapping("/5MostEarn")
    public List<OwnerResponse> getTop5MostEarnedOwners(){
        return ownerService.getTop5MostEarnedOwners();
    }

    @GetMapping("/10MostEarn")
    public List<OwnerResponse> getTop10MostEarnedOwners(){
        return ownerService.getTop10MostEarnedOwners();
    }
}
