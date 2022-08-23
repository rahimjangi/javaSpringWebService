package com.raiseup.javaSpringWebService.data.serviceImpl;

import com.raiseup.javaSpringWebService.data.dto.AddressDto;
import com.raiseup.javaSpringWebService.data.entity.AddressEntity;
import com.raiseup.javaSpringWebService.data.entity.UserEntity;
import com.raiseup.javaSpringWebService.data.repository.AddressRepository;
import com.raiseup.javaSpringWebService.data.repository.UserRepository;
import com.raiseup.javaSpringWebService.data.service.AddressService;
import com.raiseup.javaSpringWebService.exception.ErrorMessages;
import com.raiseup.javaSpringWebService.exception.UserServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceDataJpa  implements AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressServiceDataJpa(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<AddressDto> getUserAddresses(String userId) {
        ModelMapper modelMapper= new ModelMapper();
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if(optionalUserEntity.isEmpty()){
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserEntity userEntity = optionalUserEntity.get();
        List<AddressEntity> userEntityAddresses = userEntity.getAddresses();
        List<AddressDto> addressDtos= new ArrayList<>();
        userEntityAddresses.forEach(addressEntity -> {
            AddressDto addressDto= new AddressDto();
            modelMapper.map(addressEntity,addressDto);
            addressDtos.add(addressDto);
        });

        return addressDtos;
    }
}
