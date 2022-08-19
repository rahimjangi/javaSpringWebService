package com.raiseup.javaSpringWebService.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raiseup.javaSpringWebService.SpringApplicationContext;
import com.raiseup.javaSpringWebService.data.dto.UserDto;
import com.raiseup.javaSpringWebService.data.service.UserService;
import com.raiseup.javaSpringWebService.data.serviceImpl.UserServiceDataJPA;
import com.raiseup.javaSpringWebService.ui.model.request.UserLoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final  AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserLoginRequestModel creds=new ObjectMapper()
                    .readValue(request.getInputStream(),UserLoginRequestModel.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        creds.getEmailAddress(),
                        creds.getPassword(),
                        new ArrayList<>()
                    )
            );

        }catch (IOException ioException){
            throw new RuntimeException(ioException);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
                .compact();

        UserService userService= (UserService) SpringApplicationContext.getBean(UserServiceDataJPA.class);
        UserDto userDto = userService.getUser(userName);

        response.setHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+token);
        response.addHeader("UserID",userDto.getUserId());
    }


}
