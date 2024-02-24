package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.*;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.*;
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
    private CardRepository cardRepository;
    private ShareRatioRepository shareRatioRepository;
    private OwnerRepository ownerRepository;
    private MenuRepository menuRepository;

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public String setAnOrder(Long id, OrderCreateRequest request, String cardNumber){
        Customer customer = findCustomer(id);
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElse(null);
        FoodDrink foodDrink = foodDrinkRepository.findById(request.getFoodDrinkId()).orElse(null);

        Card card = cardRepository.findCardByCustomerIdAndCardNumber(id,cardNumber).orElse(null);

        if(card == null){
            return "You have no such a card has this card number.";
        }else{
            if(foodDrink != null && restaurant != null){
                if(card.getBalance() >= foodDrink.getSalesPrice()){
                    Order order = new Order();
                    Menu menu = menuRepository.findById(request.getMenuId()).orElse(null);
                    FoodDrink foodDrink1 = foodDrinkRepository.findById(request.getFoodDrinkId()).orElse(null);
                    order.setCustomer(customer);
                    order.setRestaurant(restaurant);
                    order.setMenu(menu);
                    order.setFoodDrink(foodDrink1);
                    orderRepository.save(order);

                    // Money is added to the bank account of the restaurant
                    restaurant.setNetEndorsement(restaurant.getNetEndorsement() + foodDrink.getSalesPrice());
                    restaurant.setNetProfit(restaurant.getNetProfit() + foodDrink.getProfit());
                    restaurantRepository.save(restaurant);

                    // ShareRatio process
                    List<ShareRatio> shareRatioList = shareRatioRepository.findShareRatioByRestaurantId(restaurant.getId());
                    for (int i = 0; i < shareRatioList.size(); i++) {
                        double shareRatio = shareRatioList.get(i).getShareRatio();
                        Owner owner = ownerRepository.findById(shareRatioList.get(i).getOwner().getId()).orElse(null);
                        owner.setBalance(owner.getBalance() + foodDrink.getProfit()*shareRatio);
                        ownerRepository.save(owner);

                    }
                    card.setBalance(card.getBalance()- foodDrink.getSalesPrice());
                    cardRepository.save(card);
                    return "Your order is processed on the system.";
                }
                return "You have not enough balance for this order.";
            }

            return "There is a problem here. Please check the inputs.";
        }

    }

    public List<OrderResponse> getAllTheOrdersOfTheCustomer(Long id){
        //Customer customer = findCustomer(id);
        //List<Order> orders = orderRepository.findAll();
//
        //ArrayList<Order> customerOrders = new ArrayList<>();
//
        //for (int i = 0; i < orders.size(); i++) {
        //    if(orders.get(i).getCustomer() == customer){
        //       customerOrders.add(orders.get(i));
        //    }
        //}
        List<Order> ordersList = orderRepository.findOrdersByCustomerId(id);
        return ordersList.stream().map(order -> new OrderResponse(order)).collect(Collectors.toList());
        //return customerOrders.stream().map(order -> new OrderResponse(order)).collect(Collectors.toList());
    }

    public List<OrderResponse> getAllTheOrdersOfTheRestaurant(Long restaurantId){
        //Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        //List<Order> orders = orderRepository.findAll();
//
        //ArrayList<Order> restaurantOrders = new ArrayList<>();
//
        //for(int i = 0; i < orders.size(); i++) {
        //    if(orders.get(i).getRestaurant() == restaurant){
        //        restaurantOrders.add(orders.get(i));
        //    }
        //}
//
        //return restaurantOrders.stream().map(order -> new OrderResponse(order)).collect(Collectors.toList());
        List<Order> ordersList = orderRepository.findOrdersByRestaurantId(restaurantId);
        return ordersList.stream().map(order -> new OrderResponse(order)).collect(Collectors.toList());
    }

    public String deleteAnOrder(Long id,Long orderId) {

        LocalDateTime currentDate = LocalDateTime.now();

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null && order.getCustomer().getId().equals(id)) {

            Duration duration = Duration.between(order.getDate(), currentDate);
            long minutesDifference = duration.toMinutes();

            if(minutesDifference < 1){
                orderRepository.deleteById(orderId);
                return "Your order was deleted successfully.";
            }else{
                return "It is too late to delete the order. 1 minutes lasted.";
            }

        } else if (order != null && !(order.getCustomer().getId().equals(id))) {
            return "Forbidden request!";
        }

        return "There is no such an order you have.";
    }

    public String updateTheOrder(Long id, Long orderId, OrderUpdateRequest request){

        LocalDateTime currentDate = LocalDateTime.now();

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null && order.getCustomer().getId().equals(id)) {

            Duration duration = Duration.between(order.getDate(), currentDate);
            long minutesDifference = duration.toMinutes();

            if(minutesDifference <15){
                Menu menu = menuRepository.findById(request.getMenuId()).orElse(null);
                FoodDrink foodDrink = foodDrinkRepository.findById(request.getFoodDrinkId()).orElse(null);
                order.setMenu(menu);
                order.setFoodDrink(foodDrink);
                orderRepository.save(order);

                return "Your order was updated successfully.";

            }else{
                return "It is too late to update the order. 15 minutes lasted.";
            }

        } else if (order != null && !(order.getCustomer().getId().equals(id))) {
            return "Forbidden request!";
        }

        return "There is no such an order you have.";
    }
}
