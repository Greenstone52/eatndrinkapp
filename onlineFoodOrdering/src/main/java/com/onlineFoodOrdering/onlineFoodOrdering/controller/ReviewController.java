package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Review;
import com.onlineFoodOrdering.onlineFoodOrdering.request.ReviewCreateRequest;
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

    @GetMapping("/customers/{customerId}")
    public List<ReviewResponse> getAllTheReviewsOfTheCustomer(@PathVariable Long customerId){
        return reviewService.getAllTheReviewsOfTheCustomer(customerId);
    }

    @GetMapping("/restaurants/{restaurantId}")
    public List<ReviewResponse> getAllTheReviewsOfTheRestaurant(@PathVariable Long restaurantId){
        return reviewService.getAllTheReviewsOfTheRestaurant(restaurantId);
    }

    @PostMapping("/{id}/{restaurantId}")
    public String addAReview(@PathVariable Long id, @RequestBody ReviewCreateRequest review, @PathVariable Long restaurantId){
        return reviewService.addAReview(id,review,restaurantId);
    }

    @PutMapping("/{id}/{reviewId}")
    public String updateReview(@PathVariable Long id, @RequestBody ReviewCreateRequest review,@PathVariable Long reviewId){
        return reviewService.updateReview(id,review,reviewId);
    }

    @DeleteMapping("/{id}/{reviewId}")
    public String deleteReview(@PathVariable Long id, @PathVariable Long reviewId){
        return reviewService.deleteReview(id,reviewId);
    }
}
