package com.raiseup.javaSpringWebService.repository.service;

import com.raiseup.javaSpringWebService.io.entity.UserEntity;
import com.raiseup.javaSpringWebService.shared.dto.UserDto;
import com.raiseup.javaSpringWebService.repository.UserDtoRepository;
import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class UserDtoService {
    private final UserDtoRepository userDtoRepository;
    private UserDto userDto= new UserDto();
    private UserResponse userResponse= new UserResponse();
    private UserEntity userEntity= new UserEntity();

    public UserDtoService(UserDtoRepository userDtoRepository) {
        this.userDtoRepository = userDtoRepository;

    }

    public UserResponse save(UserDetailsRequestModel userDetailsRequestModel) {
        BeanUtils.copyProperties(userDetailsRequestModel, userDto);
        userDto.setUserId("uyt6745iuty");
        userDto.setEmailValidationToken("emailvalidationtokengoeshere!");
        userDto.setEncryptedPassword("passwordshouldencryptedandgoeshere!");
        userDto.setEmailVerificationStatus(false);
        BeanUtils.copyProperties(userDto,userEntity);
//        TODO: Add business logic here!

        userEntity =userDtoRepository.save(userEntity);
        BeanUtils.copyProperties(userEntity,userResponse);
        return userResponse;
    }
}
