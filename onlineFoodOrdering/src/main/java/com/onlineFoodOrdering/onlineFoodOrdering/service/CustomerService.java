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

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllTheCustomers(){
        return customerRepository.findAll();
    }

    public Customer[] getTopNOrderedMostCustomer(int num){
        List<Customer> top5 = customerRepository.findAll();
        Customer[] result = new Customer[5];

        double res = 0;
        int count = 0;
        Long index = 0L;
        Customer customer = null;

        while(count != num){

            for (Long i = 0L; i < customerRepository.count(); i++) {
                if(customerRepository.findById(i).get().getTotalSpendMoney() > res){
                    res = customerRepository.findById(i).get().getTotalSpendMoney();
                    index = i;
                }
            }

            customer = customerRepository.findById(index).orElse(null);
            result[count] = customer;
            top5.remove(index);
            res = 0;
            count++;
        }


        return result;
    }

    public Customer[] getTop5OrderedMostCustomer(){
        return getTopNOrderedMostCustomer(5);
    }

    public Customer[] getTop10OrderedMostCustomer(){
        return getTopNOrderedMostCustomer(10);
    }


    public void addACustomer(Long id,CustomerCreateRequest request){
        Customer newCustomer = new Customer();

        DetailsOfUser details = new DetailsOfUser();
        details.setBirthDate(request.getBirthDate());
        details.setGender(request.getGender());
        details.setFirstName(request.getFirstName());
        details.setLastName(request.getLastName());
        details.setGsm(request.getGsm());
        newCustomer.setDetailsOfUser(details);
        newCustomer.setEmail(request.getEmail());
        newCustomer.setPassword(request.getPassword());

        customerRepository.save(newCustomer);
    }

    public void updateCustomerInfo(Long id, CustomerUpdateRequest request){
        Customer updatedCustomer = findCustomer(id);

        DetailsOfUser details = new DetailsOfUser();
        details.setBirthDate(request.getBirthdate());
        details.setGender(request.getGender());
        details.setFirstName(request.getFirstName());
        details.setLastName(request.getLastName());
        details.setGsm(request.getGsm());
        updatedCustomer.setDetailsOfUser(details);

        customerRepository.save(updatedCustomer);
    }

    public void updatePassword(){

    }


    // The constraint about password will be added.
    public String deleteCustomer(Long id){

      Customer customer = findCustomer(id);

      if(customer == null){
          return "There is no such an user.";
      }

      String email = customer.getEmail();
      customerRepository.deleteById(customer.getId());
      return "The user whose email is "+email+" was removed from the system.";
    }


}