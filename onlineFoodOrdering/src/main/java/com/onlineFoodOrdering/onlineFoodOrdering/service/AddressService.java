package com.onlineFoodOrdering.onlineFoodOrdering.service;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import com.onlineFoodOrdering.onlineFoodOrdering.entity.Customer;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.AddressNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.AddressTitleAlreadyExistsException;
import com.onlineFoodOrdering.onlineFoodOrdering.exception.CustomerNotFoundException;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.AddressRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.repository.CustomerRepository;
import com.onlineFoodOrdering.onlineFoodOrdering.request.AddressCreateRequest;
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
    public void addAnAddress(Long id, AddressCreateRequest address){
        Customer customer = findCustomer(id);

        Address address1 = addressRepository.findAddressByCustomerIdAndAddressTitle(id,address.getAddressTitle()).orElse(null);

        if(address1 != null){
            throw new AddressTitleAlreadyExistsException("You already use this address title for another address.");
        }

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
        }else{
            throw new CustomerNotFoundException("There is no such a customer.");
        }
    }

    public String updateAnAddress(Long id, String addressTitle, AddressUpdateRequest request) throws Exception{
        Customer customer = customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException("There is no such an customer."));

        Address existAddress = addressRepository
                .findAddressByCustomerIdAndAddressTitle(customer.getId(),addressTitle)
                .orElseThrow(()-> new AddressNotFoundException("There is no such an address has this title."));

        Address address = addressRepository.findAddressByCustomerIdAndAddressTitle(customer.getId(),request.getAddressTitle()).orElse(null);
        if(address != null){
            throw new AddressTitleAlreadyExistsException("The address has title has already exists.");
        }

        existAddress.setAddressTitle(request.getAddressTitle());
        existAddress.setBuildingNo(request.getBuildingNo());
        existAddress.setDistrict(request.getDistrict());
        existAddress.setProvince(request.getProvince());
        existAddress.setFlatNo(request.getFlatNo());
        existAddress.setNeighborhood(request.getNeighborhood());
        existAddress.setStreet(request.getStreet());

        addressRepository.save(existAddress);

        return "The address is updated successfully.";
    }

    public String deleteAnAddress(Long id,String addressTitle){
        Customer customer = findCustomer(id);

        if(customer == null){
            return "There is no such a customer.";
        }

        Address address = addressRepository
                .findAddressByCustomerIdAndAddressTitle(id,addressTitle)
                .orElseThrow(()-> new AddressNotFoundException("There is no such an address."));

        addressRepository.deleteById(address.getId());
        return "The address removed from the system.";
    }

    public AddressResponse getOneAddressOfTheCustomerAndAddressTitle(Long id,String title){

        if(customerRepository.findById(id) == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }else{
            Address address = addressRepository.findAddressByCustomerIdAndAddressTitle(id,title).orElseThrow(() -> new AddressNotFoundException("There is no such an address has this title."));
            AddressResponse response = new AddressResponse(address);
            return response;
        }

    }

    public List<AddressResponse> getAllTheAddressOfTheCustomer(Long id){

        if(customerRepository.findById(id) == null){
            throw new CustomerNotFoundException("There is no such a customer.");
        }else{
            Customer customer = findCustomer(id);
            List<Address> addresses = addressRepository.findAddressByCustomerId(id);
            return addresses.stream().map(address -> new AddressResponse(address)).collect(Collectors.toList());
        }
    }

}
