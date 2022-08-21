package com.raiseup.javaSpringWebService.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = {UserServiceException.class})
    public ResponseEntity<Object>handleUserServiceException(UserServiceException ex, WebRequest request){
//        CustomUserErrorMessage customUserErrorMessage= new CustomUserErrorMessage(
//
//                Arrays.stream(ex.getStackTrace()).filter(item->item.equals("className")).collect(Collectors.joining(", ")),
//                Arrays.stream(ex.getStackTrace()).findFirst().filter(item->item.equals("methodName")).get().toString(),
//                new Date(),
//                ex.getMessage()
//
//        );
        List<StackTraceElement> traceElements = Arrays.stream(ex.getStackTrace())
                .filter(item->item.toString().startsWith("com.raiseup."))
                .collect(Collectors.toList());
        traceElements.forEach(element-> System.out.println(element));

        return new ResponseEntity<>(ex.getMessage() ,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object>handleOtherExceptions(Exception ex, WebRequest request){

        return new ResponseEntity<>(null,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
