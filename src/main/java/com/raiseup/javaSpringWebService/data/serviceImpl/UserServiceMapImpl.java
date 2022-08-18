package com.raiseup.javaSpringWebService.data.serviceImpl;

import com.raiseup.javaSpringWebService.data.service.UserService;
import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("map")
public class UserServiceMapImpl implements UserService {
    @Override
    public UserResponse save(UserDetailsRequestModel userDetails) {
        log.warn("MAP implementation...");
        return null;
    }
}
