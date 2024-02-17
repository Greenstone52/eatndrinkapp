package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.request.OrderCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OrderUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OrderResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private OrderService orderService;

    @GetMapping("/customers/{id}")
    public List<OrderResponse> getAllTheOrdersOfTheCustomer(@PathVariable Long id){
        return orderService.getAllTheOrdersOfTheCustomer(id);
    }

    @GetMapping("/restaurants/{restaurantId}")
    public List<OrderResponse> getAllTheOrdersOfTheRestaurant(@PathVariable Long restaurantId){
        return orderService.getAllTheOrdersOfTheRestaurant(restaurantId);
    }

    @PostMapping("/{id}/{cardNumber}")
    public String setAnOrder(@PathVariable Long id, @RequestBody OrderCreateRequest request,@PathVariable String cardNumber){
        return orderService.setAnOrder(id,request,cardNumber);
    }

    @PutMapping("/{customerId}/{orderId}")
    public String updateTheOrder(@PathVariable Long customerId,@PathVariable Long orderId,@RequestBody OrderUpdateRequest request){
        return orderService.updateTheOrder(customerId,orderId,request);
    }

    @DeleteMapping("/{id}/{orderId}")
    public String deleteAnOrder(@PathVariable Long id,@PathVariable Long orderId) {
        return orderService.deleteAnOrder(id,orderId);
    }
}
