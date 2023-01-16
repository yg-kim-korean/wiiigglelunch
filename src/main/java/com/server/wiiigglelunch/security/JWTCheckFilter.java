package com.server.wiiigglelunch.security;


import com.server.wiiigglelunch.service.UsersService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTCheckFilter extends BasicAuthenticationFilter {
    private UsersService usersService;
    public JWTCheckFilter(AuthenticationManager authenticationManager, UsersService usersService) {

        super(authenticationManager);
        this.usersService = usersService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException { //토큰 검사
        super.doFilterInternal(request, response, chain);
    }
}
