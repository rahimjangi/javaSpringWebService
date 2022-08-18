package com.raiseup.javaSpringWebService.data.serviceImpl;

import com.raiseup.javaSpringWebService.data.dto.UserDto;
import com.raiseup.javaSpringWebService.data.entity.UserEntity;
import com.raiseup.javaSpringWebService.data.repository.UserRepository;
import com.raiseup.javaSpringWebService.data.service.UserService;
import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("jpa")
public class UserServiceDataJPA implements UserService {
    private final UserRepository userRepository;

    public UserServiceDataJPA(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse save(UserDetailsRequestModel userDetails) {
        UserDto userDto = new UserDto();
        UserResponse userResponse= new UserResponse();
        BeanUtils.copyProperties(userDetails, userDto);
        userDto.setUserId("uyguksf");
        userDto.setEncryptedPassword("kujghakjvf");
        userDto.setEmailValidationToken("kjahskjcf");
        userDto.setEmailVerificationStatus(false);
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        UserEntity savedUsed=userRepository.save(userEntity);
        BeanUtils.copyProperties(savedUsed,userResponse);
        return userResponse;
    }
}
