package com.onlineFoodOrdering.onlineFoodOrdering.response;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Review;
import lombok.Data;

@Data
public class ReviewResponse {
    private String customerName;
    private String restaurantName;
    private String title;
    private String text;
    private short point;

    public ReviewResponse(Review review){
        customerName = review.getCustomer().getDetailsOfUser().getFirstName().substring(0,1) +"*** "
                + review.getCustomer().getDetailsOfUser().getLastName().substring(0,1) + "***";
        restaurantName = review.getRestaurant().getName();
        title = review.getTitle();
        text = review.getText();
        point = review.getPoint();
    }

}
