package com.raiseup.javaSpringWebService.repository;

import com.raiseup.javaSpringWebService.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserDtoRepository extends CrudRepository<UserEntity,Long> {
}
