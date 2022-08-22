package com.raiseup.javaSpringWebService.ui.controller;

import com.raiseup.javaSpringWebService.data.dto.AddressDto;
import com.raiseup.javaSpringWebService.data.service.UserService;
import com.raiseup.javaSpringWebService.exception.ErrorMessages;
import com.raiseup.javaSpringWebService.exception.UserServiceException;
import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.AddressResponse;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "users",
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
        )
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>>getUsers(@RequestParam(name = "page",defaultValue = "1")int page,
                                                      @RequestParam(name = "limit",defaultValue = "5")int limit){
       return new ResponseEntity<>(userService.getUsers(page,limit),HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserResponse>getUser(@PathVariable("userId")String userId){
        return new ResponseEntity<>(userService.findByUserId(userId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse>saveUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        if(userDetails.getFirstName().isEmpty()||
                userDetails.getEmailAddress().isEmpty()||
                userDetails.getLastName().isEmpty()||
                userDetails.getPassword().isEmpty()) throw  new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        UserResponse response=userService.save(userDetails);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserDetailsRequestModel user){
        UserResponse userResponse = userService.updateUser(user);
        return new ResponseEntity<>(userResponse,HttpStatus.OK);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(@PathVariable(name = "userId") String userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(null,HttpStatus.ACCEPTED);
    }

    @GetMapping("{userId}/addresses")
    public ResponseEntity<List<AddressResponse>>getUserAddresses(@PathVariable("userId")String userId){
        List<AddressDto> addressDtos=userService.getUserAddresses(userId);
        List<AddressResponse>responses= new ArrayList<>();
        ModelMapper modelMapper= new ModelMapper();
        addressDtos.forEach(addressDto -> {
            AddressResponse addressResponse= new AddressResponse();
            modelMapper.map(addressDto,addressResponse);
            responses.add(addressResponse);
        });
        return new ResponseEntity<>(responses,HttpStatus.OK);
    }
}
