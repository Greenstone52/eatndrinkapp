package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.BankAccount;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.DetailsOfUser;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Owner;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class OwnerResponse {

    private DetailsOfUser detailsOfUser;
    private BankAccount bankAccount;
    private List<Restaurant> restaurants;
    public OwnerResponse(Owner owner){
        this.detailsOfUser = owner.getDetailsOfUser();
        this.bankAccount = owner.getBankAccount();
        this.restaurants = owner.getRestaurants();
    }
}
