package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CustomerDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.UserUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CustomerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController2 {
    private CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomers(){
        return customerService.getAllTheCustomers();
    }

    //Denenecek
    @GetMapping("/{num}")
    public Customer[] getTopNOrderedMostCustomer(@PathVariable int num){
        return customerService.getTopNOrderedMostCustomer(num);
    }

    //Denenecek
    @GetMapping("/top5")
    public Customer[] getTop5OrderedMostCustomer(){
        return customerService.getTop5OrderedMostCustomer();
    }

    //Denenecek
    @GetMapping("/top10")
    public Customer[] getTop10OrderedMostCustomer(){
        return customerService.getTop10OrderedMostCustomer();
    }

    //@PostMapping
    //public void addACustomer(@RequestBody AuthenticationRequest request){
    //    customerService.addACustomer(request);
    //}

    @PutMapping("/{id}")
    public void updateCustomerInfo(@PathVariable Long id, @RequestBody UserUpdateRequest request){
        customerService.updateCustomerInfo(id,request);
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, @RequestBody CustomerDeleteRequest request){
        return customerService.deleteCustomer(id,request);
    }
}
