package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.exception.*;
import com.onlineFoodOrdering.onlineFoodOrdering.request.*;
import com.onlineFoodOrdering.onlineFoodOrdering.response.*;
import com.onlineFoodOrdering.onlineFoodOrdering.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @DeleteMapping("/customers/delete/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId){
        return customerService.deleteCustomer(customerId);
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

    //@PostMapping("/reviews/{customerId}/{restaurantId}")
    //public String addAReview(@PathVariable Long customerId, @RequestBody ReviewCreateRequest review, @PathVariable Long restaurantId){
    //    return reviewService.addAReview(customerId,review,restaurantId);
    //}

    @PostMapping("/reviews/customers/{customerId}/orders/{orderId}")
    public String addAReview(@PathVariable Long customerId, @RequestBody ReviewCreateRequest review, @PathVariable Long orderId){
        return reviewService.addAReview(customerId,review,orderId);
    }

    @PutMapping("/reviews/{reviewId}/customers/{customerId}")
    public String updateReview(@PathVariable Long customerId, @RequestBody ReviewCreateRequest review,@PathVariable Long reviewId){
        return reviewService.updateReview(customerId,review,reviewId);
    }

    @DeleteMapping("/reviews/{reviewId}/customers/{customerId}")
    public String deleteReview(@PathVariable Long customerId, @PathVariable Long reviewId){
        return reviewService.deleteReview(customerId,reviewId);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handleCustomerNotFoundException(CustomerNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<String> handleCardNotFoundException(CardNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<String> handleAddressNotFoundException(AddressNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(RestaurantNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<String> handleReviewNotFoundException(ReviewNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressTitleAlreadyExistsException.class)
    public ResponseEntity<String> handleAddressTitleAlreadyExistsException(AddressTitleAlreadyExistsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CardTitleAlreadyExistsException.class)
    public ResponseEntity<String> handleCardTitleAlreadyExistsException(CardTitleAlreadyExistsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CardForbiddenValuesException.class)
    public ResponseEntity<String> handleCardForbiddenValuesException(CardForbiddenValuesException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserForbiddenValuesException.class)
    public ResponseEntity<String> handleCustomerForbiddenValuesException(UserForbiddenValuesException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<String> handleInvalidValueException(InvalidValueException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ImpossibleOrderException.class)
    public ResponseEntity<String> handleImpossibleOrderException(ImpossibleOrderException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FoodDrinkNotFoundException.class)
    public ResponseEntity<String> handleFoodDrinkNotFoundException(FoodDrinkNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderIsNotYoursException.class)
    public ResponseEntity<String> handleOrderIsNotYoursException(OrderIsNotYoursException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OrderCannotBeCancelledException.class)
    public ResponseEntity<String> handleOrderCannotBeCancelledException(OrderCannotBeCancelledException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ReviewAlreadyExistsException.class)
    public ResponseEntity<String> handleReviewAlreadyExistsException(ReviewAlreadyExistsException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalReviewRequestException.class)
    public ResponseEntity<String> handleIllegalReviewRequestException(IllegalReviewRequestException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ReviewIsNotYoursException.class)
    public ResponseEntity<String> handleReviewIsNotYoursException(ReviewIsNotYoursException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}
