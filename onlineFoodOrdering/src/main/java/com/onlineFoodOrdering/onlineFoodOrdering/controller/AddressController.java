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
    public List<AddressResponse> getAllAddressesOfTheCustomer(@RequestParam Long customerId){
        return addressService.getAllTheAddressOfTheCustomer(customerId);
    }

    @GetMapping("/{customerId}/{title}")
    public AddressResponse getOneAddressByCustomerIdAndTitle(@RequestParam Long customerId, @RequestParam String title){
        return addressService.getOneAddressOfTheCustomerAndAddressTitle(customerId,title);
    }

    @PostMapping("/{customerId}")
    public void postAnAddress(@RequestParam Long customerId, @RequestBody Address address){
        addressService.addAnAddress(customerId,address);
    }

    @PutMapping("/{id}/{addressTitle}")
    public void updateAnAddress(@RequestParam Long id, @RequestParam String addressTitle, @RequestBody AddressUpdateRequest request){
        addressService.updateAnAddress(id,addressTitle,request);
    }

    @DeleteMapping("/{id}/{addressTitle}")
    public void deleteAnAddress(@RequestParam Long id, @RequestParam String addressTitle){
        addressService.deleteAnAddress(id,addressTitle);
    }
}
