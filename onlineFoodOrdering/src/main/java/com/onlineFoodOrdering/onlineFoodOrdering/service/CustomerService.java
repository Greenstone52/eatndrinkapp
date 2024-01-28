package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.*;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.*;
import com.onlineFoodOrdering.onlineFoodOrdering.request.*;
import com.onlineFoodOrdering.onlineFoodOrdering.response.AddressResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OrderResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.ReviewResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;

    public void updateCustomerInfo(String username, CustomerUpdateRequest request){
        Customer customer = findCustomer(username);
        customer.getDetailsOfUser().setBirthDate(request.getBirthdate());
        customer.getDetailsOfUser().setGsm(request.getGsm());
        customer.getDetailsOfUser().setGender(request.getGender());
        customer.getDetailsOfUser().setFirstName(request.getFirstName());
        customer.getDetailsOfUser().setLastName(request.getLastName());
    }

    //public String deleteCustomer(String username){

      //Customer customer = findCustomer(username);

      //if(customer == null){
      //    return "There is no user whose username is "+username+".";
      //}

      //String username2 = customer.getUsername();
      //customerRepository.deleteById(customer.getId());
      //return "The user whose username is "+username2+" was removed from the system.";
    //}

    public Customer findCustomer(String username){
        return customerRepository.findCustomerByUsername(username).orElse(null);
    }
}