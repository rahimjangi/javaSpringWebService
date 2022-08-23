package com.raiseup.javaSpringWebService.data.service;

import com.raiseup.javaSpringWebService.data.dto.AddressDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {
    List<AddressDto> getUserAddresses(String userId);

    AddressDto getAddress(String userId, String addressId);


}
