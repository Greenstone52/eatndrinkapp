package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    boolean existsRestaurantByName(String name);
    List<Restaurant> findRestaurantByType(String type);
}
