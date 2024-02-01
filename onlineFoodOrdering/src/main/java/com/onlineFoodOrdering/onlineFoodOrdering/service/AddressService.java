package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.AddressRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CustomerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.AddressUpdateRequest;
import com.onlineFoodOrdering.onlineFoodOrdering.response.AddressResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AddressService {

    private AddressRepository addressRepository;
    private CustomerRepository customerRepository;

    public Customer findCustomer(Long id){
        return customerRepository.findById(id).orElse(null);
    }
    public void addAnAddress(Long id,Address address){
        Customer customer = findCustomer(id);

        if(customer != null){
            Address newAddress = new Address();
            newAddress.setAddressTitle(address.getAddressTitle());
            newAddress.setCustomer(customer);
            newAddress.setDistrict(address.getDistrict());
            newAddress.setNeighborhood(address.getNeighborhood());
            newAddress.setProvince(address.getProvince());
            newAddress.setFlatNo(address.getFlatNo());
            newAddress.setStreet(address.getStreet());
            newAddress.setBuildingNo(address.getBuildingNo());
            addressRepository.save(newAddress);
        }
    }

    public void updateAnAddress(Long id, String addressTitle, AddressUpdateRequest request){
        Customer customer = findCustomer(id);
        Address existAddress = addressRepository
                .findAddressByCustomerIdAndAddressTitle(customer.getId(),addressTitle)
                .orElse(null);

        existAddress.setAddressTitle(request.getAddressTitle());
        existAddress.setBuildingNo(request.getBuildingNo());
        existAddress.setDistrict(request.getDistrict());
        existAddress.setProvince(request.getProvince());
        existAddress.setFlatNo(request.getFlatNo());
        existAddress.setNeighborhood(request.getNeighborhood());
        existAddress.setStreet(request.getStreet());

        addressRepository.save(existAddress);
    }

    public String deleteAnAddress(Long id,String addressTitle){
        Customer customer = findCustomer(id);

        Address address = addressRepository
                .findAddressByCustomerIdAndAddressTitle(customer.getId(),addressTitle)
                .orElse(null);

        if(address == null){
            return "There is no such an address.";
        }else{
            return "The address removed from the system.";
        }

    }

    public List<AddressResponse> getAllTheAddressOfTheCustomer(Long id){
        Customer customer = findCustomer(id);
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream().map(address -> new AddressResponse(address)).collect(Collectors.toList());
    }

}