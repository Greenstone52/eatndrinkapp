package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.exception.*;
import com.onlineFoodOrdering.onlineFoodOrdering.request.InvalidPasswordException;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerResponseWithoutSRRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.UserUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CustomerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OwnerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CustomerDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.service.CustomerService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.ManagerAdminService;
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
    private ManagerAdminService managerAdminService;

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

    // Update
    @PutMapping("/{id}")
    public String updateOneManagerAdmin(@PathVariable Long id, @RequestBody UserUpdateRequest request){
        return managerAdminService.updateOneManagerAdmin(id,request);
    }

    @PostMapping("/delete/{id}")
    public String deleteOneManagerAdmin(@PathVariable Long id, @RequestBody CustomerDeleteRequest request){
        return managerAdminService.deleteManagerAdmin(id,request);
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(RestaurantNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<String> handleIncorrectPasswordException(IncorrectPasswordException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handleInvalidPasswordException(InvalidPasswordException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<String> handleInvalidValueException(InvalidValueException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserForbiddenValuesException.class)
    public ResponseEntity<String> handleUserForbiddenValuesException(UserForbiddenValuesException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.FORBIDDEN);
    }
}
