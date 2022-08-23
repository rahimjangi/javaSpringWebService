package com.raiseup.javaSpringWebService.data.serviceImpl;

import com.raiseup.javaSpringWebService.data.dto.UserDto;
import com.raiseup.javaSpringWebService.data.entity.AddressEntity;
import com.raiseup.javaSpringWebService.data.entity.UserEntity;
import com.raiseup.javaSpringWebService.data.repository.UserRepository;
import com.raiseup.javaSpringWebService.data.service.UserService;
import com.raiseup.javaSpringWebService.exception.ErrorMessages;
import com.raiseup.javaSpringWebService.exception.UserServiceException;
import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import com.raiseup.javaSpringWebService.utils.UtilityHelper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private UserEntity userEntity= new UserEntity();
    private UserResponse userResponse= new UserResponse();
    private UserDto userDto= new UserDto();
    ModelMapper modelMapper= new ModelMapper();


    public UserServiceDataJPA(UserRepository userRepository, UtilityHelper helper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.helper = helper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserResponse save(UserDetailsRequestModel userDetails) {
        if (userRepository.findByEmailAddress(userDetails.getEmailAddress()).isPresent()) try {
            throw new UserServiceException(ErrorMessages.EMAIL_ADDRESS_NOT_VERIFIED.getErrorMessage());
        } catch (Exception e) {

        }

        modelMapper.map(userDetails,userDto);
        userDto.setUserId(helper.generateUserId(50));
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setEmailValidationToken("kjahskjcf");
        userDto.setEmailVerificationStatus(false);

        modelMapper.map(userDto, userEntity);

       for(int i=0;i<userEntity.getAddresses().size();i++){
           AddressEntity addressEntity = userEntity.getAddresses().get(i);
           addressEntity.setUserDetails(userEntity);
           addressEntity.setAddress_id(helper.generateAddressId(30));
           userEntity.getAddresses().set(i,addressEntity);
        }
        UserEntity savedUserEntity = userRepository.save(userEntity);
        modelMapper.map(savedUserEntity, userResponse);
        return userResponse;
    }

    @Override
    public UserDto getUser(String emailAddress) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailAddress(emailAddress);
        if (optionalUserEntity.isEmpty()) {
            throw  new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        ModelMapper modelMapper= new ModelMapper();
        UserEntity userEntity= optionalUserEntity.get();
        UserDto userDto= new UserDto();
        modelMapper.map(userEntity, userDto);
        return userDto;
    }

    @Override
    public UserResponse getUser(UserDetailsRequestModel user) {
        modelMapper.map(user, userEntity);
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailAddress(userEntity.getEmailAddress());
        assert optionalUserEntity.orElse(null) != null;
        modelMapper.map(userResponse, optionalUserEntity.orElse(null));
        return userResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailAddress(emailAddress);
        if (optionalUserEntity.isEmpty()) throw new UsernameNotFoundException("User does not exist!");
        UserEntity userEntity = optionalUserEntity.get();
        return new User(userEntity.getEmailAddress(), userEntity.getEncryptedPassword(), new ArrayList<>());
    }

    @Override
    public UserResponse findByUserId(String userId) {
        Optional<UserEntity> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isPresent()) {
            userEntity = optionalUser.get();
            modelMapper.map(userEntity, userResponse);
            return userResponse;
        }
        return null;
    }

    @Override
    public List<UserResponse> getUsers(int page,int limit) {
        List<UserResponse> userResponses = new ArrayList<>();
        if(page>0) page-=1;
        Pageable pageRequest= PageRequest.of(page, limit);
        Page<UserEntity> userEntityPage = userRepository.findAll(pageRequest);
        List<UserEntity> content = userEntityPage.getContent();
        content.forEach(item -> {
            UserResponse userResponse= new UserResponse();
            modelMapper.map(item, userResponse);
            userResponses.add(userResponse);
            System.out.println(userResponse.getEmailAddress());
        });
        return userResponses;
    }


    @Override
    public UserResponse updateUser(UserDetailsRequestModel user) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByEmailAddress(user.getEmailAddress());
        if (optionalUserEntity.isPresent()) {
            userEntity = optionalUserEntity.get();
            userEntity.setFirstName(user.getFirstName());
            userEntity.setLastName(user.getLastName());
            UserEntity savedUserEntity = userRepository.save(userEntity);
            modelMapper.map(savedUserEntity, userResponse);
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
