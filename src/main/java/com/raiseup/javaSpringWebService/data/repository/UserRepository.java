package com.raiseup.javaSpringWebService.data.repository;

import com.raiseup.javaSpringWebService.data.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
    Optional<UserEntity>findByEmailAddress(String emailAddress);
}
