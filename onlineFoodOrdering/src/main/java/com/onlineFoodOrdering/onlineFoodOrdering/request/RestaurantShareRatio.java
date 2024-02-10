package com.onlineFoodOrdering.onlineFoodOrdering.request;

import jdk.jfr.DataAmount;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantShareRatio {
    private String restaurantName;
    private double shareRatio;
}
