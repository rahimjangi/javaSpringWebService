package com.raiseup.javaSpringWebService.ui.controller;

import com.raiseup.javaSpringWebService.data.service.UserService;
import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>>getUsers(){
       return new ResponseEntity<>(userService.getUsers(),HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserResponse>getUser(@PathVariable("userId")String userId){
        return new ResponseEntity<>(userService.findByUserId(userId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse>saveUser(@RequestBody UserDetailsRequestModel userDetails){
        UserResponse response=userService.save(userDetails);

        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        return null;
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Long> deleteUser(){
        return null;
    }
}
