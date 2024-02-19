package com.onlineFoodOrdering.onlineFoodOrdering.controllerRoles;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.request.*;
import com.onlineFoodOrdering.onlineFoodOrdering.response.*;
import com.onlineFoodOrdering.onlineFoodOrdering.service.*;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@AllArgsConstructor
public class CustomerController {

    private AddressService addressService;
    private CardService cardService;
    private CustomerService customerService;
    private OrderService orderService;
    private ReviewService reviewService;

    // Address
    @GetMapping("/addresses/{customerId}")
    public List<AddressResponse> getAllAddressesOfTheCustomer(@PathVariable Long customerId){
        return addressService.getAllTheAddressOfTheCustomer(customerId);
    }

    @GetMapping("/addresses/{customerId}/{title}")
    public AddressResponse getOneAddressByCustomerIdAndTitle(@PathVariable Long customerId, @PathVariable String title){
        return addressService.getOneAddressOfTheCustomerAndAddressTitle(customerId,title);
    }

    @PostMapping("/addresses/{customerId}")
    public void postAnAddress(@PathVariable Long customerId, @RequestBody AddressCreateRequest address){
        addressService.addAnAddress(customerId,address);
    }

    @PutMapping("/addresses/{customerId}/{addressTitle}")
    public String updateAnAddress(@PathVariable Long customerId, @PathVariable String addressTitle, @RequestBody AddressUpdateRequest request) throws Exception{
        return addressService.updateAnAddress(customerId,addressTitle,request);
    }

    @DeleteMapping("/addresses/{customerId}/{addressTitle}")
    public String deleteAnAddress(@PathVariable Long customerId, @PathVariable String addressTitle){
        return addressService.deleteAnAddress(customerId,addressTitle);
    }

    // Card
    @GetMapping("/cards/{customerId}")
    public List<CardResponse> getAllTheCardsOfTheCustomer(@PathVariable Long customerId){
        return cardService.getAllTheCardsOfTheCustomer(customerId);
    }

    @PostMapping("/cards/{customerId}")
    public void setACard(@PathVariable Long customerId, @RequestBody CardCreateRequest card){
        cardService.setACard(customerId,card);
    }

    @PutMapping("/cards/{customerId}/{cardNumber}")
    public void updateSelectedCard(@PathVariable Long customerId,@PathVariable String cardNumber,@RequestBody CardCreateRequest updateCard){
        cardService.updateSelectedCard(customerId,cardNumber,updateCard);
    }

    @PostMapping("/cards/delete/{customerId}")
    public String deleteACard(@PathVariable Long customerId, @RequestBody CardDeleteRequest request){
        return cardService.deleteACard(customerId,request);
    }

    // Customer
    @PutMapping("/customers/{customerId}")
    public void updateCustomerInfo(@PathVariable Long customerId, @RequestBody UserUpdateRequest request){
        customerService.updateCustomerInfo(customerId,request);
    }

    @PostMapping("/customers/delete/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId, @RequestBody CustomerDeleteRequest request){
        return customerService.deleteCustomer(customerId,request);
    }

    // Order
    @GetMapping("/orders/customers/{id}")
    public List<OrderResponse> getAllTheOrdersOfTheCustomer(@PathVariable Long id){
        return orderService.getAllTheOrdersOfTheCustomer(id);
    }

    @PostMapping("/orders/{customerId}/{cardNumber}")
    public String setAnOrder(@PathVariable Long customerId, @RequestBody OrderCreateRequest request,@PathVariable String cardNumber){
        return orderService.setAnOrder(customerId,request,cardNumber);
    }

    @PutMapping("/orders/{customerId}/{orderId}")
    public String updateTheOrder(@PathVariable Long customerId,@PathVariable Long orderId,@RequestBody OrderUpdateRequest request){
        return orderService.updateTheOrder(customerId,orderId,request);
    }

    @DeleteMapping("/orders/{customerId}/{orderId}")
    public String deleteAnOrder(@PathVariable Long customerId,@PathVariable Long orderId) {
        return orderService.deleteAnOrder(customerId,orderId);
    }

    // Review
    @GetMapping("/reviews/customers/{customerId}")
    public List<ReviewResponse> getAllTheReviewsOfTheCustomer(@PathVariable Long customerId){
        return reviewService.getAllTheReviewsOfTheCustomer(customerId);
    }

    @PostMapping("/reviews/{customerId}/{restaurantId}")
    public String addAReview(@PathVariable Long customerId, @RequestBody ReviewCreateRequest review, @PathVariable Long restaurantId){
        return reviewService.addAReview(customerId,review,restaurantId);
    }

    @PutMapping("/reviews/{customerId}/{reviewId}")
    public String updateReview(@PathVariable Long customerId, @RequestBody ReviewCreateRequest review,@PathVariable Long reviewId){
        return reviewService.updateReview(customerId,review,reviewId);
    }

    @DeleteMapping("/reviews/{customerId}/{reviewId}")
    public String deleteReview(@PathVariable Long customerId, @PathVariable Long reviewId){
        return reviewService.deleteReview(customerId,reviewId);
    }
}
