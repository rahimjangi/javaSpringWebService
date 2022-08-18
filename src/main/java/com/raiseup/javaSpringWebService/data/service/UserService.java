package com.raiseup.javaSpringWebService.data.service;

import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface  UserService {

    UserResponse save(UserDetailsRequestModel userDetails);
}
