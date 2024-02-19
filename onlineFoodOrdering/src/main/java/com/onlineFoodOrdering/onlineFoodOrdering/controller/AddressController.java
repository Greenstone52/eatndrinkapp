package com.onlineFoodOrdering.onlineFoodOrdering.controller;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import com.onlineFoodOrdering.onlineFoodOrdering.request.AddressCreateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.request.AddressUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.AddressResponse;
import com.onlineFoodOrdering.onlineFoodOrdering.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private AddressService addressService;

    @GetMapping("/{customerId}")
    public List<AddressResponse> getAllAddressesOfTheCustomer(@PathVariable Long customerId){
        return addressService.getAllTheAddressOfTheCustomer(customerId);
    }

    @GetMapping("/{customerId}/{title}")
    public AddressResponse getOneAddressByCustomerIdAndTitle(@PathVariable Long customerId, @PathVariable String title){
        return addressService.getOneAddressOfTheCustomerAndAddressTitle(customerId,title);
    }

    @PostMapping("/{customerId}")
    public void postAnAddress(@PathVariable Long customerId, @RequestBody AddressCreateRequest address){
        addressService.addAnAddress(customerId,address);
    }

    @PutMapping("/{customerId}/{addressTitle}")
    public String updateAnAddress(@PathVariable Long customerId, @PathVariable String addressTitle, @RequestBody AddressUpdateRequest request) throws Exception{
        return addressService.updateAnAddress(customerId,addressTitle,request);
    }

    @DeleteMapping("/{customerId}/{addressTitle}")
    public String deleteAnAddress(@PathVariable Long customerId, @PathVariable String addressTitle){
        return addressService.deleteAnAddress(customerId,addressTitle);
    }
}
