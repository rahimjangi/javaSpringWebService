package com.raiseup.javaSpringWebService.data.serviceImpl;

import com.raiseup.javaSpringWebService.data.dto.UserDto;
import com.raiseup.javaSpringWebService.data.entity.UserEntity;
import com.raiseup.javaSpringWebService.data.repository.UserRepository;
import com.raiseup.javaSpringWebService.data.service.UserService;
import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import com.raiseup.javaSpringWebService.utils.UtilityHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("jpa")
public class UserServiceDataJPA implements UserService {
    private final UserRepository userRepository;
    private final UtilityHelper helper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceDataJPA(UserRepository userRepository, UtilityHelper helper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.helper = helper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserResponse save(UserDetailsRequestModel userDetails) {
        if(userRepository.findByEmailAddress(userDetails.getEmailAddress()).isPresent()) try {
            throw new Exception("Email is taken!");
        } catch (Exception e) {

        }

        UserDto userDto = new UserDto();
        UserResponse userResponse= new UserResponse();
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(userDetails, userDto);
        userDto.setUserId(helper.generateUserId(50));
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setEmailValidationToken("kjahskjcf");
        userDto.setEmailVerificationStatus(false);
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity=userRepository.save(userEntity);
        BeanUtils.copyProperties(userEntity,userResponse);

        return userResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
