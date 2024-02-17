package com.onlineFoodOrdering.onlineFoodOrdering.request;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import lombok.Data;

@Data
public class ReviewCreateRequest {
    private String title;
    private String text;
    private short point;
}
