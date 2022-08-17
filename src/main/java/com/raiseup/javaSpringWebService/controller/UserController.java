package com.raiseup.javaSpringWebService.controller;

import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping
    public ResponseEntity<List<User>>getUsers(){
        return null;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User>getUser(@PathVariable("userId")Long userId){
        return null;
    }
    @PostMapping
    public ResponseEntity<User>saveUser(@RequestBody User user){
        return null;
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
