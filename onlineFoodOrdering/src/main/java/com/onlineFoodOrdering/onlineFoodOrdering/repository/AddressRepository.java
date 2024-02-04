package com.onlineFoodOrdering.onlineFoodOrdering.repository;

import com.onlineFoodOrdering.onlineFoodOrdering.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    Optional<Address> findAddressByCustomerIdAndAddressTitle(Long customerId,String addressTitle);
}
