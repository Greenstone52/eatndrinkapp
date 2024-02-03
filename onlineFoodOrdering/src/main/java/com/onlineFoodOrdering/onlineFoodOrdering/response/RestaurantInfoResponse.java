package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Menu;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantInfoResponse {
    private String name;
    private String province;
    private String district;
    //private List<Menu> menus;

    public RestaurantInfoResponse(Restaurant restaurant){
        this.name = restaurant.getName();
        this.province = restaurant.getProvince();
        this.district = restaurant.getDistrict();
    }
}
