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

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public String setAnOrder(Long id, OrderCreateRequest request, String cardNumber){
        Customer customer = findCustomer(id);
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElse(null);
        FoodDrink foodDrink = foodDrinkRepository.findById(request.getFoodDrinkId()).orElse(null);
        List<Card> cards = cardRepository.findCardByCustomerId(id);
        Card card = new Card();

        for (int i = 0; i < cards.size(); i++) {
            if(cards.get(i).getCardNumber().equals(cardNumber)){
                card = cards.get(i);
                break;
            }
        }

        if(card == null){
            return "You have no such a card has this card number.";
        }else{
            if(foodDrink != null && restaurant != null){
                if(card.getBalance() >= foodDrink.getSalesPrice()){
                    Order order = new Order();
                    order.setCustomer(customer);
                    order.setRestaurant(restaurant);
                    order.setMenuId(request.getMenuId());
                    order.setFoodDrinkId(request.getFoodDrinkId());
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

                    return "Your order is processed on the system.";
                }
                return "You have no enough balance for this order";
            }

            return "There is a problem here. Please check the inputs";
        }

    }

    public List<OrderResponse> getAllTheOrdersOfTheCustomer(Long id){
        Customer customer = findCustomer(id);
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

    public String deleteAnOrder(Long id,Long orderId) {
        Customer customer = findCustomer(id);

        LocalDateTime currentDate = LocalDateTime.now();

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null && order.getCustomer().getId().equals(id)) {

            Duration duration = Duration.between(order.getDate(), currentDate);
            long minutesDifference = duration.toMinutes();

            if(minutesDifference < 15){
                orderRepository.deleteById(orderId);
                return "Your order was deleted successfully.";
            }else{
                return "It is too late to delete the order. 15 minutes lasted.";
            }

        } else if (order != null && !(order.getCustomer().getId().equals(id))) {
            return "Forbidden request!";
        }

        return "There is no such an order you have.";
    }

    public String updateTheOrder(Long id, Long orderId, OrderUpdateRequest request){
        Customer customer = findCustomer(id);

        LocalDateTime currentDate = LocalDateTime.now();

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order != null && order.getCustomer().getId().equals(customer.getId())) {

            Duration duration = Duration.between(order.getDate(), currentDate);
            long minutesDifference = duration.toMinutes();

            if(minutesDifference <15){
                order.setMenuId(request.getMenuId());
                order.setFoodDrinkId(request.getFoodDrinkId());
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
