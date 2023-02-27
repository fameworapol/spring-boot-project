package com.example.EP1Springboot.config.token;

import com.example.EP1Springboot.service.TokenService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//กำหนด configurer ให้กับ TokenFilter
public class TokenFilterConfiguerer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenService tokenService;

    public TokenFilterConfiguerer(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        TokenFilter filter = new TokenFilter(tokenService);
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
