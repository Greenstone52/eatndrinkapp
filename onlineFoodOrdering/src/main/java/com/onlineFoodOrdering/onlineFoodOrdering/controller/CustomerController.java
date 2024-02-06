package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CustomerDeleteRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.CustomerUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CustomerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.security.auth.AuthenticationRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private CustomerService customerService;

    @GetMapping
    public List<CustomerResponse> getAllCustomers(){
        return customerService.getAllTheCustomers();
    }

    @GetMapping("/{num}")
    public Customer[] getTopNOrderedMostCustomer(@RequestParam int num){
        return customerService.getTopNOrderedMostCustomer(num);
    }

    @GetMapping("/top5")
    public Customer[] getTop5OrderedMostCustomer(){
        return customerService.getTop5OrderedMostCustomer();
    }

    @GetMapping("/top10")
    public Customer[] getTop10OrderedMostCustomer(){
        return customerService.getTop10OrderedMostCustomer();
    }

    @PostMapping
    public void addACustomer(@RequestBody AuthenticationRequest request){
        customerService.addACustomer(request);
    }

    @PutMapping("/{id}")
    public void updateCustomerInfo(@RequestParam Long id, @RequestBody CustomerUpdateRequest request){
        customerService.updateCustomerInfo(id,request);
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@RequestParam Long id, @RequestBody CustomerDeleteRequest request){
        return customerService.deleteCustomer(id,request);
    }
}
