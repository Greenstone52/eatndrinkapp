package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.*;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.*;
import com.onlineFoodOrdering.onlineFoodOrdering.request.*;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CustomerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.security.auth.RegisterRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.security.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

            // Password will be decoded here before go to next lines.

            if(user.getPassword().equals(request.getPassword())){
                customer.getDetailsOfUser().setGender(request.getGender());
                customer.getDetailsOfUser().setGsm(request.getGsm());
                customer.getDetailsOfUser().setLastName(request.getLastName());
                customer.getDetailsOfUser().setFirstName(request.getLastName());
                customer.getDetailsOfUser().setBirthDate(request.getBirthDate());
                detailsOfUserRepository.save(customer.getDetailsOfUser());

                return "The details of the owner was updated successfully.";
            }

            return "The password entered is wrong.";

        }else{
            return "There is no such an owner in the system.";
        }

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