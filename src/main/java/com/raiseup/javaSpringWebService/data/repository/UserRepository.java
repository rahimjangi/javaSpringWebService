package com.raiseup.javaSpringWebService.data.repository;

import com.raiseup.javaSpringWebService.data.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity,Long> {
}
