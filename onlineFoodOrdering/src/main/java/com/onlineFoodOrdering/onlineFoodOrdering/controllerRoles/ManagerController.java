package com.onlineFoodOrdering.onlineFoodOrdering.controllerRoles;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.request.OwnerResponseWithoutSRRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.CustomerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.response.OwnerResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.CustomerService;
import com.onlineFoodOrdering.onlineFoodOrdering.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manager")
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
    public Customer[] getTopNOrderedMostCustomer(@PathVariable int num){
        return customerService.getTopNOrderedMostCustomer(num);
    }

    //Denenecek
    @GetMapping("/customers/top5")
    public Customer[] getTop5OrderedMostCustomer(){
        return customerService.getTop5OrderedMostCustomer();
    }

    //Denenecek
    @GetMapping("/customers/top10")
    public Customer[] getTop10OrderedMostCustomer(){
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

    @GetMapping("/owners/top5")
    public List<OwnerResponse> getTopFiveOwners(){
        return ownerService.getTopFiveOwners();
    }

    @GetMapping("/owners/top10")
    public List<OwnerResponse> getTop10Owners(){
        return ownerService.getTop10Owners();
    }

    @GetMapping("/owners/{topN}")
    public List<OwnerResponse> getTopNOwners(@PathVariable Long topN){
        return ownerService.getTopNOwners(topN);
    }

}
