package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import lombok.Data;

@Data
public class ReviewUpdateRequest {
    private Restaurant restaurant;
    private String title;
    private String text;
    private short point;
}
