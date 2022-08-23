package com.raiseup.javaSpringWebService.data.repository;

import com.raiseup.javaSpringWebService.data.entity.AddressEntity;
import com.raiseup.javaSpringWebService.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity,Long> {
    Iterable<AddressEntity>findAllByUserDetails(UserEntity userDetails);
}
