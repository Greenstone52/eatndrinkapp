package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.*;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.*;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.*;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OrderCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OrderUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OrderResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
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
    private AddressRepository addressRepository;

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public String setAnOrder(Long id, OrderCreateRequest request, String cardNumber){

        if(findCustomer(id) == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }

        if(addressRepository.findAddressByCustomerId(id).isEmpty()){
            throw new AddressNotFoundException("You cannot order anything since you have not any address saved.");
        }else if(addressRepository.findAddressByCustomerIdAndAddressTitle(id,request.getAddressTitle()).orElse(null) == null){
            throw new AddressNotFoundException("There is no such an address saved in the system.");
        }

        Address address = addressRepository.findAddressByCustomerIdAndAddressTitle(id,request.getAddressTitle()).get();
//
        FoodDrink foodDrink = foodDrinkRepository.findById(request.getFoodDrinkId()).orElseThrow(()-> new FoodDrinkNotFoundException("There is no such a food or a drink."));
        Restaurant restaurant = foodDrink.getMenu().getRestaurant();

        Card card = cardRepository.findCardByCustomerIdAndCardNumber(id,cardNumber).orElseThrow(()->new CardNotFoundException("There is no such a card has this card number"));

        if(card.getBalance() >= foodDrink.getSalesPrice()){

            Order order = new Order();
            order.setCustomer(findCustomer(id));
            order.setRestaurant(foodDrink.getMenu().getRestaurant());
            order.setMenu(foodDrink.getMenu());
            order.setFoodDrink(foodDrink);
            order.setCard(card);

            // Customer statistics
            Customer customer = findCustomer(id);
            customer.setTotalNumberOfOrder(customer.getTotalNumberOfOrder()+1);
            customer.setTotalSpendMoney(customer.getTotalSpendMoney() + foodDrink.getSalesPrice());
            customerRepository.save(customer);

            order.setAddress(address);
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
                owner.setBalance(owner.getBalance() + foodDrink.getProfit()*(shareRatio/100));
                ownerRepository.save(owner);
            }

            card.setBalance(card.getBalance()- foodDrink.getSalesPrice());
            cardRepository.save(card);
            return "Your order is processed on the system.";
        }
        return "You have not enough balance for this order.";
    }

    public List<OrderResponse> getAllTheOrdersOfTheCustomer(Long id){

        if(findCustomer(id) == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }

        List<Order> ordersList = orderRepository.findOrdersByCustomerId(id);
        return ordersList.stream().map(order -> new OrderResponse(order)).collect(Collectors.toList());
    }

    public List<OrderResponse> getAllTheOrdersOfTheRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException("There is no such a restaurant."));
        List<Order> ordersList = orderRepository.findOrdersByRestaurantId(restaurantId);
        return ordersList.stream().map(order -> new OrderResponse(order)).collect(Collectors.toList());
    }

    public String deleteAnOrder(Long id,Long orderId) {

        LocalDateTime currentDate = LocalDateTime.now();

        Customer customer = customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("There is no such a customer."));
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("There is no such an order."));

        if (order != null && order.getCustomer().getId().equals(id)) {

            Duration duration = Duration.between(order.getDate(), currentDate);
            long minutesDifference = duration.toMinutes();

            if(minutesDifference < 10){

                order.getRestaurant().setNetEndorsement(order.getRestaurant().getNetEndorsement() - order.getFoodDrink().getSalesPrice());
                order.getRestaurant().setNetProfit(order.getRestaurant().getNetProfit() - order.getFoodDrink().getProfit());
                restaurantRepository.save(order.getRestaurant());


                List<ShareRatio> shareRatioList = shareRatioRepository.findShareRatioByRestaurantId(order.getRestaurant().getId());

                for (int i = 0; i < shareRatioList.size(); i++) {
                    double shareRatio = shareRatioList.get(i).getShareRatio();
                    Owner owner = ownerRepository.findById(shareRatioList.get(i).getOwner().getId()).orElse(null);
                    owner.setBalance(owner.getBalance() - order.getFoodDrink().getProfit()*(shareRatio/100));
                    ownerRepository.save(owner);
                }

                order.getCard().setBalance(order.getCard().getBalance() + order.getFoodDrink().getSalesPrice());
                cardRepository.save(order.getCard());

                orderRepository.deleteById(orderId);

                // Customer statistics
                customer.setTotalNumberOfOrder(customer.getTotalNumberOfOrder()-1);
                customer.setTotalSpendMoney(customer.getTotalSpendMoney() - order.getFoodDrink().getSalesPrice());
                customerRepository.save(customer);

                return "Your order was deleted successfully.";
            }else{
                return "It is too late to delete the order. 10 minutes lasted.";
            }

        } else if (order != null && !(order.getCustomer().getId().equals(id))) {
            return "Forbidden request!";
        }

        return "There is no such an order you have.";
    }

    public String updateTheOrder(Long id, Long orderId, OrderUpdateRequest request){

        LocalDateTime currentDate = LocalDateTime.now();
        Customer customer = findCustomer(id);

        Order order = orderRepository.findById(orderId).orElseThrow(()-> new OrderNotFoundException("There is no such an order"));

        FoodDrink fdDrink = foodDrinkRepository.findById(request.getFoodDrinkId()).orElseThrow(()-> new FoodDrinkNotFoundException("There is no such a food or drink."));
        Restaurant restaurant = fdDrink.getMenu().getRestaurant();

        if(!(restaurant == order.getRestaurant())){
            throw new IllegalOrderException("Please choose a food or drink from the same restaurant.");
        }

        if (order.getCustomer().getId().equals(id)) {

            Duration duration = Duration.between(order.getDate(), currentDate);
            long minutesDifference = duration.toMinutes();

            if(minutesDifference <10){

                // Old Order's profit get back from the accounts.
                order.getRestaurant().setNetEndorsement(order.getRestaurant().getNetEndorsement() - order.getFoodDrink().getSalesPrice());
                order.getRestaurant().setNetProfit(order.getRestaurant().getNetProfit() - order.getFoodDrink().getProfit());
                customer.setTotalSpendMoney(customer.getTotalSpendMoney()-order.getFoodDrink().getSalesPrice());
                restaurantRepository.save(order.getRestaurant());

                order.getCard().setBalance(order.getCard().getBalance() + order.getFoodDrink().getSalesPrice());
                cardRepository.save(order.getCard());

                List<ShareRatio> shareRatioList = shareRatioRepository.findShareRatioByRestaurantId(order.getRestaurant().getId());

                for (int i = 0; i < shareRatioList.size(); i++) {
                    double shareRatio = shareRatioList.get(i).getShareRatio();
                    Owner owner = ownerRepository.findById(shareRatioList.get(i).getOwner().getId()).orElse(null);
                    owner.setBalance(owner.getBalance() - order.getFoodDrink().getProfit()*(shareRatio/100));
                    ownerRepository.save(owner);
                }

                //New Order's operation
                FoodDrink foodDrink = foodDrinkRepository.findById(request.getFoodDrinkId()).orElseThrow(()-> new FoodDrinkNotFoundException("There is no such a food or drink."));
                order.setMenu(foodDrink.getMenu());
                order.setFoodDrink(foodDrink);
                order.setDate(LocalDateTime.now());
                orderRepository.save(order);

                // Money is added to the bank account of the restaurant
                order.getRestaurant().setNetEndorsement(order.getRestaurant().getNetEndorsement() + foodDrink.getSalesPrice());
                order.getRestaurant().setNetProfit(order.getRestaurant().getNetProfit() + foodDrink.getProfit());
                restaurantRepository.save(order.getRestaurant());

                // ShareRatio process
                List<ShareRatio> shareRatioList2 = shareRatioRepository.findShareRatioByRestaurantId(order.getRestaurant().getId());

                for (int i = 0; i < shareRatioList2.size(); i++) {
                    double shareRatio = shareRatioList2.get(i).getShareRatio();
                    Owner owner = ownerRepository.findById(shareRatioList2.get(i).getOwner().getId()).orElse(null);
                    owner.setBalance(owner.getBalance() + foodDrink.getProfit()*(shareRatio/100));
                    ownerRepository.save(owner);
                }

                order.getCard().setBalance(order.getCard().getBalance()- foodDrink.getSalesPrice());
                cardRepository.save(order.getCard());
                orderRepository.save(order);


                customerRepository.save(customer);

                customer.setTotalSpendMoney(customer.getTotalSpendMoney() + order.getFoodDrink().getSalesPrice());
                return "Your order was updated successfully.";

            }else{
                throw new OrderCannotBeCancelledException("It is too late to update the order. 10 minutes lasted.");
            }

        } else {
            throw new OrderIsNotYoursException("Forbidden request!");
        }
    }
}
