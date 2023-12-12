package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodDrinkRepository extends JpaRepository<FoodDrink,Long> {
}
