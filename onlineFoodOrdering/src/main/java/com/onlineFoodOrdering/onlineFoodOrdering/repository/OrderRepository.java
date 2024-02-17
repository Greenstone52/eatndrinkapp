package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Long> {
    Optional<Order> findOrderByCustomerIdAndRestaurantId(Long customerId, Long restaurantId);
    List<Order> findOrdersByCustomerId(Long customerId);
    List<Order> findOrdersByRestaurantId(Long restaurantId);
}
