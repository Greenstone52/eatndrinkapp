package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
}
