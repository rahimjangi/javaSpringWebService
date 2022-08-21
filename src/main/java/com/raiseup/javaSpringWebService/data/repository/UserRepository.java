package com.raiseup.javaSpringWebService.data.repository;

import com.raiseup.javaSpringWebService.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity>findByEmailAddress(String emailAddress);
    Optional<UserEntity>findByUserId(String userId);
}
