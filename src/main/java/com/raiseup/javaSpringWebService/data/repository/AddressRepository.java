package com.raiseup.javaSpringWebService.data.repository;

import com.raiseup.javaSpringWebService.data.entity.AddressEntity;
import com.raiseup.javaSpringWebService.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    Iterable<AddressEntity>findAllByUserDetails(UserEntity userDetails);
    Optional<AddressEntity>findByAddressId(String addressId);
}
