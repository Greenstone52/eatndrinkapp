package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Review;
import lombok.Data;

@Data
public class ReviewResponse {
    private String restaurantName;
    private String title;
    private String text;
    private short point;

    public ReviewResponse(Review review){
        restaurantName = review.getRestaurant().getName();
        title = review.getTitle();
        text = review.getText();
        point = review.getPoint();
    }

}
