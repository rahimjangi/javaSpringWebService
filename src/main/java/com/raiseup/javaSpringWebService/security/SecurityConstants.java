package com.raiseup.javaSpringWebService.security;

import com.raiseup.javaSpringWebService.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME=360000;
    public static final String TOKEN_PREFIX="Bearer ";
    public static final String HEADER_STRING="Authorization";
    public static final String SIGN_UP_URL="/users";


    public static String getTokenSecret(){
        AppProperties properties= (AppProperties) SpringApplicationContext.getBean(AppProperties.class);
        return properties.getTokenSecret();
    }

}
