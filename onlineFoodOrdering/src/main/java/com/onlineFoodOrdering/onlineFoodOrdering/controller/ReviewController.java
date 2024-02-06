package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Review;
import com.onlineFoodOrdering.onlineFoodOrdering.response.ReviewResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private ReviewService reviewService;

    @GetMapping
    public List<ReviewResponse> getAllTheReviewsOfTheCustomer(@PathVariable Long id){
        return reviewService.getAllTheReviewsOfTheCustomer(id);
    }

    @GetMapping("/{restaurantId}")
    public List<ReviewResponse> getAllTheReviewsOfTheRestaurant(@PathVariable Long restaurantId){
        return reviewService.getAllTheReviewsOfTheRestaurant(restaurantId);
    }

    @PostMapping("/{id}&{restaurantId}")
    public String addAReview(@PathVariable Long id, @RequestBody Review review, @PathVariable Long restaurantId){
        return reviewService.addAReview(id,review,restaurantId);
    }

    @PutMapping("/{id}/{restaurantId}")
    public String updateReview(@PathVariable Long id, @RequestBody Review review,@PathVariable Long restaurantId){
        return reviewService.updateReview(id,review,restaurantId);
    }

    @DeleteMapping("/{id}/{restaurantId}")
    public String deleteReview(@PathVariable Long id, @PathVariable Long restaurantId){
        return reviewService.deleteReview(id,restaurantId);
    }
}
