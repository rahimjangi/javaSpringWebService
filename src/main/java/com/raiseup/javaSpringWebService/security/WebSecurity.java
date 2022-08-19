package com.raiseup.javaSpringWebService.security;

import com.raiseup.javaSpringWebService.data.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
public class WebSecurity {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;

    public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);



        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and();
//        .addFilter(new AuthenticationFilter(this.filterChain());
        return http.build();
    }




}
