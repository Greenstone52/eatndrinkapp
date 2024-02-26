package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.RestaurantNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerResponseWithoutSRRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CustomerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OwnerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.CustomerService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/management")
@AllArgsConstructor
public class ManagerController {

    private CustomerService customerService;
    private OwnerService ownerService;

    // Customer

    @GetMapping("/customers")
    public List<CustomerResponse> getAllCustomers(){
        return customerService.getAllTheCustomers();
    }

    //Denenecek
    @GetMapping("/customers/{num}")
    public List<CustomerResponse> getTopNOrderedMostCustomer(@PathVariable Long num){
        return customerService.getTopNOrderedMostCustomer(num);
    }

    //Denenecek
    @GetMapping("/customers/top5")
    public List<CustomerResponse> getTop5OrderedMostCustomer(){
        return customerService.getTop5OrderedMostCustomer();
    }

    //Denenecek
    @GetMapping("/customers/top10")
    public List<CustomerResponse> getTop10OrderedMostCustomer(){
        return customerService.getTop10OrderedMostCustomer();
    }

    // Owner

    @GetMapping("/owners")
    public List<OwnerResponse> getAllOwners(){
        return ownerService.getAllOwners();
    }

    @GetMapping("/owners/{restaurantId}")
    public List<OwnerResponseWithoutSRRequest> getOwnersByRestaurantId(@PathVariable Long restaurantId){
        return ownerService.getOwnersByRestaurantId(restaurantId);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(RestaurantNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

}
