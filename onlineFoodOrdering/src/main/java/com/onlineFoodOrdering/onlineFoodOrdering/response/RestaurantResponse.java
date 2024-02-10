package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import lombok.Data;

@Data
public class RestaurantResponse {
    private String name;
    private String type;
    private String province;
    private String district;

    public RestaurantResponse(Restaurant restaurant){
        this.name = restaurant.getName();
        this.type = restaurant.getType();
        this.province = restaurant.getProvince();
        this.district = restaurant.getDistrict();
    }
}
