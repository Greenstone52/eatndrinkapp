package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Optional<Review> findReviewByCustomerIdAndRestaurantId(Long customerId,Long restaurantId);
    List<Review> findReviewsByCustomerId(Long customerId);
    List<Review> findReviewsByRestaurantId(Long restaurantId);
}
