package com.raiseup.javaSpringWebService.ui.controller;

import com.raiseup.javaSpringWebService.data.dto.AddressDto;
import com.raiseup.javaSpringWebService.data.service.AddressService;
import com.raiseup.javaSpringWebService.data.service.UserService;
import com.raiseup.javaSpringWebService.exception.ErrorMessages;
import com.raiseup.javaSpringWebService.exception.UserServiceException;
import com.raiseup.javaSpringWebService.ui.model.request.UserDetailsRequestModel;
import com.raiseup.javaSpringWebService.ui.model.response.AddressResponse;
import com.raiseup.javaSpringWebService.ui.model.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/users",
        consumes = {MediaType.ALL_VALUE},
        produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}
        )
public class UserController {
    private final UserService userService;
    private final AddressService addressService;

    public UserController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>>getUsers(@RequestParam(name = "page",defaultValue = "1",required = false)int page,
                                                      @RequestParam(name = "limit",defaultValue = "5",required = false)int limit){
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
    public ResponseEntity<CollectionModel<AddressResponse>>getUserAddresses(@PathVariable("userId")String userId){
        List<AddressDto> addressDtos=addressService.getUserAddresses(userId);
        List<AddressResponse>responses= new ArrayList<>();
        ModelMapper modelMapper= new ModelMapper();
        addressDtos.forEach(addressDto -> {
            AddressResponse addressResponse= new AddressResponse();
            modelMapper.map(addressDto,addressResponse);
            responses.add(addressResponse);
        });

        for(AddressResponse addressResponse:responses){
            Link addressSelfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
                    .getAddress(userId, addressResponse.getAddressId())).withSelfRel();
            addressResponse.add(addressSelfLink);
        }

        Link selfRel = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(userId)).withSelfRel();
        CollectionModel<AddressResponse> collectionModel = CollectionModel.of(responses, Arrays.asList(selfRel));

        return new ResponseEntity<>(collectionModel,HttpStatus.OK);
    }

//
//    @GetMapping("{userId}/addresses")
//    public ResponseEntity<List<EntityModel<AddressResponse>>>getUserAddresses(@PathVariable("userId")String userId){
//        List<AddressDto> addressDtos=addressService.getUserAddresses(userId);
//        List<AddressResponse>responses= new ArrayList<>();
//        ModelMapper modelMapper= new ModelMapper();
//        addressDtos.forEach(addressDto -> {
//            AddressResponse addressResponse= new AddressResponse();
//            modelMapper.map(addressDto,addressResponse);
//            responses.add(addressResponse);
//        });
//
//        List<EntityModel<AddressResponse>> entityModelList=new ArrayList<>();
//        for(AddressResponse addressResponse:responses){
//            Link addressSelfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class)
//                    .getAddress(userId, addressResponse.getAddressId())).withSelfRel();
//            EntityModel<AddressResponse> responseEntityModel = EntityModel.of(addressResponse, addressSelfLink);
//            entityModelList.add(responseEntityModel);
//        }
//
//        Link selfRel = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(userId)).withSelfRel();
//
//        return new ResponseEntity<>(entityModelList,HttpStatus.OK);
//    }





    @GetMapping("{userId}/addresses/{addressId}")
    public ResponseEntity<EntityModel<AddressResponse>>getAddress(@PathVariable("userId")String userId, @PathVariable("addressId")String addressId){
        AddressResponse addressResponse= new AddressResponse();
        ModelMapper modelMapper= new ModelMapper();
        AddressDto addressDto=addressService.getAddress(userId,addressId);
        modelMapper.map(addressDto,addressResponse);
        Link selfRel = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getAddress(userId,addressId)).withSelfRel();
        Link userLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUser(userId)).withRel("user");
        Link addressesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserController.class).getUserAddresses(userId)).withRel("addresses");
//        addressResponse.add(userLink,addressesLink,selfRel);

        EntityModel<AddressResponse> entityModel = EntityModel.of(addressResponse, Arrays.asList(userLink, addressesLink, selfRel));

        return new ResponseEntity<>(entityModel,HttpStatus.OK);
    }
}
