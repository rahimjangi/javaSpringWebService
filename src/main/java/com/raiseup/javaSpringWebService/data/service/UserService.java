package com.raiseup.javaSpringWebService.data.service;

import com.raiseup.javaSpringWebService.data.dto.UserDto;
import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface  UserService extends UserDetailsService {

    UserResponse save(UserDetailsRequestModel userDetails);
    UserDto getUser(String emailAddress);
    UserResponse getUser(UserDetailsRequestModel user);
    UserResponse findByUserId(String userId);
    UserResponse updateUser(UserDetailsRequestModel user);
    void deleteUser(String userId);
    List<UserResponse> getUsers(int page, int limit);
}
