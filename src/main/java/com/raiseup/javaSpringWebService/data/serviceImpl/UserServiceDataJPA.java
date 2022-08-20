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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public UserDto getUser(String emailAddress) {
        Optional<UserEntity> optionalUserEntity= userRepository.findByEmailAddress(emailAddress);
        UserEntity userEntity= new UserEntity();
        UserDto userDto= new UserDto();
        if(optionalUserEntity.isPresent()){
            userEntity= optionalUserEntity.get();
        }
        BeanUtils.copyProperties(userEntity,userDto);
        return userDto;
    }

    @Override
    public UserResponse getUser(UserDetailsRequestModel user) {
        UserEntity userEntity= new UserEntity();
        UserResponse userResponse= new UserResponse();
        BeanUtils.copyProperties(user,userEntity);
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailAddress(userEntity.getEmailAddress());
        assert optionalUserEntity.orElse(null) != null;
        BeanUtils.copyProperties(userResponse,optionalUserEntity.orElse(null));
        return userResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailAddress(emailAddress);
        if(optionalUserEntity.isEmpty()) throw new UsernameNotFoundException("User does not exist!");
        UserEntity userEntity=optionalUserEntity.get();
        return new User(userEntity.getEmailAddress(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }
    @Override
    public UserResponse findByUserId(String userId){
        UserEntity userEntity= new UserEntity();
        UserResponse userResponse= new UserResponse();
        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()){
            userEntity = optionalUser.get();
            BeanUtils.copyProperties(userEntity,userResponse);
            return userResponse;
        }
        return null;
    }

    @Override
    public List<UserResponse> getUsers() {
        UserResponse userResponse= new UserResponse();
        List<UserResponse> userResponses= new ArrayList<>();
        userRepository.findAll().forEach(item->{
            BeanUtils.copyProperties(item,userResponse);
            userResponses.add(userResponse);
        });
        return userResponses;
    }

    @Override
    public UserResponse updateUser(UserDetailsRequestModel user) {
        UserResponse userResponse= new UserResponse();
        UserEntity userEntity= new UserEntity();
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailAddress(user.getEmailAddress());
        if(optionalUserEntity.isPresent()){
            userEntity=optionalUserEntity.get();
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            UserEntity savedUserEntity = userRepository.save(userEntity);
            BeanUtils.copyProperties(savedUserEntity,userResponse);
            return userResponse;
        }
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> userEntity = userRepository.findByUserId(userId);
        userEntity.ifPresent(userRepository::delete);
    }
}
