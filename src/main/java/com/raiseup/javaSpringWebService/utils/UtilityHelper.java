package com.raiseup.javaSpringWebService.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class UtilityHelper {
    private final Random RANDOM= new SecureRandom();
    private final String ALPHABET="abcdefghijklmnopqrstuvwxyz01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";


    public String generateUserId(int length){
        return generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder returnedString= new StringBuilder(length);
        for(int i=0;i<length;i++){
            returnedString.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return returnedString.toString();
    }
}
