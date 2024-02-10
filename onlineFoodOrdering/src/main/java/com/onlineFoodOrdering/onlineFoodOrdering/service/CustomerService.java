package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.*;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.*;
import com.onlineFoodOrdering.onlineFoodOrdering.request.*;
import com.onlineFoodOrdering.onlineFoodOrdering.response.AddressResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CustomerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OrderResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.ReviewResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.security.auth.AuthenticationRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.security.enums.Role;
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
    private UserRepository userRepository;
    private DetailsOfUserRepository detailsOfUserRepository;

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<CustomerResponse> getAllTheCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> new CustomerResponse(customer)).collect(Collectors.toList());
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


    //public void addACustomer(CustomerCreateRequest request){
    //    Customer newCustomer = new Customer();
    //    User user = new User();
//
    //    DetailsOfUser details = new DetailsOfUser();
    //    details.setBirthDate(request.getBirthDate());
    //    details.setGender(request.getGender());
    //    details.setFirstName(request.getFirstName());
    //    details.setLastName(request.getLastName());
    //    details.setGsm(request.getGsm());
    //    newCustomer.setDetailsOfUser(details);
    //    user.setEmail(request.getEmail());
    //    user.setPassword(request.getPassword());
    //    user.setRole(Role.CUSTOMER);
    //    user.setUserDetailsId(newCustomer.getId());
//
    //    // UserId nasıl tutulacak !!!
    //    newCustomer.setUser(user);
//
    //    detailsOfUserRepository.save(details);
    //    userRepository.save(user);
    //    customerRepository.save(newCustomer);
    //}

    public void addACustomer(AuthenticationRequest request){
        Customer newCustomer = new Customer();
        User user = new User();

        Long userCount = userRepository.count();

        //user's data
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.CUSTOMER);
        user.setUserDetailsId(customerRepository.count() + 1);
        userRepository.save(user);

        // This means a user was enrolled
        if(userRepository.count() >= userCount){
            //details' data
            DetailsOfUser details = new DetailsOfUser();
            details.setBirthDate(request.getBirthDate());
            details.setGender(request.getGender());
            details.setFirstName(request.getFirstName());
            details.setLastName(request.getLastName());
            details.setGsm(request.getGsm());
            detailsOfUserRepository.save(details);
            newCustomer.setDetailsOfUser(details);
        }

        // UserId nasıl tutulacak !!! // otomatik artıyor zaten !!!
        newCustomer.setUser(user);
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

        detailsOfUserRepository.save(details);
        customerRepository.save(updatedCustomer);
    }

    public void updatePassword(){

    }


    // The constraint about password will be added.
    public String deleteCustomer(Long id, CustomerDeleteRequest request){

      Customer customer = findCustomer(id);
      User user = userRepository.findUserByUserDetailsIdAndRole(customer.getId(),Role.CUSTOMER);

      if(user == null){
          return "There is no such an user.";
      }

      if(user.getPassword().equals(request.getPassword())){
          String email = user.getEmail();
          customerRepository.deleteById(customer.getId());
          // userRepository.deleteById(user.getId());
          // An error may be occur here as orphanal remove is not working well.
          return "The user whose email is "+email+" was removed from the system.";
      }else{
          return "The password is entered incorrect!";
      }

    }


}