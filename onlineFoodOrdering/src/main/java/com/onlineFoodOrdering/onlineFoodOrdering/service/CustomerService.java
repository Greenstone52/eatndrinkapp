package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.*;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.CustomerNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.*;
import com.onlineFoodOrdering.onlineFoodOrdering.request.*;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CustomerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OwnerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.security.auth.RegisterRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.security.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;
    private UserRepository userRepository;
    private DetailsOfUserRepository detailsOfUserRepository;
    private PasswordEncoder passwordEncoder;

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<CustomerResponse> getAllTheCustomers(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> new CustomerResponse(customer)).collect(Collectors.toList());
    }

    public List<CustomerResponse> getTopNOrderedMostCustomer(Long num){
        //List<Customer> topN = customerRepository.findAll();
//
        //if(customerRepository.count() < num){
        //    num = (int)customerRepository.count();
        //}
//
        //Customer[] result = new Customer[num];
//
        //int count = 0;
//
        //while(count != num){
//
        //    double res = 0;
        //    Long index = 0L;
        //    Long indexOfList = 0L;
//
        //    for (Long i = 0L; i < topN.size(); i++) {
        //        if(customerRepository.findById(i).get().getTotalSpendMoney() >= res){
        //            res = customerRepository.findById(i).get().getTotalSpendMoney();
        //            index = customerRepository.findById(i).get().getId();
        //            indexOfList = i;
        //        }
        //    }
//
        //    Customer customer = customerRepository.findById(index).orElse(null);
        //    result[count] = customer;
        //    topN.remove(indexOfList);
        //    count++;
        //}
//
        //return result;

        if(num > customerRepository.count()){
            num = customerRepository.count();
        }

        List<Customer> allCustomers = customerRepository.findAll();
        ArrayList<Customer> topN = new ArrayList<>();


        for (int j = 0; j < num; j++) {
            double maxVal = 0;
            int index = 0;

            for (int i = 0; i < allCustomers.size(); i++) {
                if(allCustomers.get(i).getTotalNumberOfOrder() >= maxVal){
                    maxVal = allCustomers.get(i).getTotalNumberOfOrder();
                    index = i;
                }
            }

            topN.add(allCustomers.get(index));
            allCustomers.remove(index);
        }

        return topN.stream().map(customer -> new CustomerResponse(customer)).collect(Collectors.toList());

    }

    public List<CustomerResponse> getTop5OrderedMostCustomer(){
        return getTopNOrderedMostCustomer(5L);
    }

    public List<CustomerResponse> getTop10OrderedMostCustomer(){
        return getTopNOrderedMostCustomer(10L);
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

    public void addACustomer(RegisterRequest request){
        Customer newCustomer = new Customer();
        User user = new User();

        Long userCount = userRepository.count();

        //user's data
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        //user.setUserDetailsId(customerRepository.count() + 1);
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

        List<Customer> customers = customerRepository.findAll();
        Customer lastCustomer = customers.get(customers.size()-1);

        user.setUserDetailsId(lastCustomer.getId());
        userRepository.save(user);
        newCustomer.setUser(user);
        customerRepository.save(newCustomer);

    }

    public String updateCustomerInfo(Long id, UserUpdateRequest request){
        //Customer updatedCustomer = findCustomer(id);
//
        //DetailsOfUser details = new DetailsOfUser();
        //details.setBirthDate(request.getBirthdate());
        //details.setGender(request.getGender());
        //details.setFirstName(request.getFirstName());
        //details.setLastName(request.getLastName());
        //details.setGsm(request.getGsm());
        //updatedCustomer.setDetailsOfUser(details);
//
        //detailsOfUserRepository.save(details);
        //customerRepository.save(updatedCustomer);

        Customer customer = customerRepository.findById(id).orElse(null);
        User user = userRepository.findUserByUserDetailsIdAndRole(customer.getId(),Role.OWNER);

        if(customer != null){
            customer.getDetailsOfUser().setGender(request.getGender());
            customer.getDetailsOfUser().setGsm(request.getGsm());
            customer.getDetailsOfUser().setLastName(request.getLastName());
            customer.getDetailsOfUser().setFirstName(request.getLastName());
            customer.getDetailsOfUser().setBirthDate(request.getBirthDate());
            detailsOfUserRepository.save(customer.getDetailsOfUser());
            return "The details of the customer was updated successfully.";
        }else{
            throw new CustomerNotFoundException("There is no such a customer.");
        }

    }

    public void updatePassword(){

    }


    // The constraint about password will be added.
    public String deleteCustomer(Long id){

        Customer customer = findCustomer(id);

        if(customer == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }

        User user = userRepository.findUserByUserDetailsIdAndRole(customer.getId(),Role.CUSTOMER);

        String email = user.getEmail();
        customerRepository.deleteById(customer.getId());
        return "The user whose email is "+email+" was removed from the system.";
    }


}