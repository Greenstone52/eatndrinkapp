package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import lombok.Data;

@Data
public class RestaurantPrivateInfoResponse {
    private String name;
    private double netEndorsement;
    private double netProfit;

    public RestaurantPrivateInfoResponse(Restaurant restaurant){
        this.name = restaurant.getName();
        this.netEndorsement = restaurant.getNetEndorsement();
        this.netProfit = restaurant.getNetProfit();
    }
}
