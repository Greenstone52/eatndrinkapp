package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodDrinkRepository extends JpaRepository<FoodDrink,Long> {
    List<FoodDrink> findFoodDrinkByMenuId(Long menuId);
}
