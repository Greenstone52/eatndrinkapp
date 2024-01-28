package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.FoodDrink;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Order;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Restaurant;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CustomerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.FoodDrinkRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.OrderRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.RestaurantRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OrderCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OrderUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private RestaurantRepository restaurantRepository;
    private FoodDrinkRepository foodDrinkRepository;

    public Customer findCustomer(String username){
        return customerRepository.findCustomerByUsername(username).orElse(null);
    }

    public String setAnOrder(String username, OrderCreateRequest request){
        Customer customer = findCustomer(username);
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElse(null);
        FoodDrink foodDrink = foodDrinkRepository.findById(request.getFoodDrinkId()).orElse(null);

        if(customer != null && foodDrink != null){
            if(customer.getCard().getBalance() >= foodDrink.getPrice()){
                Order order = new Order();
                order.setCustomer(customer);
                order.setRestaurant(restaurant);
                order.setMenuId(request.getMenuId());
                order.setFoodDrinkId(request.getFoodDrinkId());
                orderRepository.save(order);

                // Money is added to the bank account of the restaurant
                restaurant.getBankAccount().setBalance(restaurant.getBankAccount().getBalance() + foodDrink.getPrice());

                return "Your order is processed on the system.";
            }
            return "You have no enough balance for this order";
        }

        return "There is a problem here. Please check the inputs";
    }

    public List<OrderResponse> getAllTheOrdersOfTheCustomer(String username){
        Customer customer = findCustomer(username);
        List<Order> orders = orderRepository.findAll();

        ArrayList<Order> customerOrders = new ArrayList<>();

        for (int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getCustomer() == customer){
               customerOrders.add(orders.get(i));
            }
        }

        return customerOrders.stream().map(order -> new OrderResponse(order)).collect(Collectors.toList());
    }

    public List<OrderResponse> getAllTheOrdersOfTheRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        List<Order> orders = orderRepository.findAll();

        ArrayList<Order> restaurantOrders = new ArrayList<>();

        for(int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getRestaurant() == restaurant){
                restaurantOrders.add(orders.get(i));
            }
        }

        return restaurantOrders.stream().map(order -> new OrderResponse(order)).collect(Collectors.toList());
    }

    public String deleteAnOrder(String username,Long orderId) {
        Customer customer = findCustomer(username);

        LocalDateTime currentDate = LocalDateTime.now();

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null && order.getCustomer().getUsername().equals(customer.getUsername())) {

            Duration duration = Duration.between(order.getDate(), currentDate);
            long minutesDifference = duration.toMinutes();

            if(minutesDifference <30){
                orderRepository.deleteById(orderId);
                return "Your order was deleted successfully.";
            }else{
                return "It is too late to delete the order. 30 minutes lasted.";
            }

        } else if (order != null && !(order.getCustomer().getUsername().equals(customer.getUsername()))) {
            return "Forbidden request!";
        }

        return "There is no such an order you have.";
    }

    public String updateTheOrder(String username, Long orderId, OrderUpdateRequest request){
        Customer customer = findCustomer(username);

        LocalDateTime currentDate = LocalDateTime.now();

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null && order.getCustomer().getUsername().equals(customer.getUsername())) {

            Duration duration = Duration.between(order.getDate(), currentDate);
            long minutesDifference = duration.toMinutes();

            if(minutesDifference <30){
                order.setMenuId(request.getMenuId());
                order.setFoodDrinkId(request.getFoodDrinkId());

                return "Your order was updated successfully.";

            }else{
                return "It is too late to update the order. 30 minutes lasted.";
            }

        } else if (order != null && !(order.getCustomer().getUsername().equals(customer.getUsername()))) {
            return "Forbidden request!";
        }

        return "There is no such an order you have.";
    }
}
