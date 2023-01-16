package com.server.wiiigglelunch.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.wiiigglelunch.configuration.JWTUtil;
import com.server.wiiigglelunch.domain.Users.UserLoginForm;
import com.server.wiiigglelunch.domain.Users.Users;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper objectMapper = new ObjectMapper();
    public JWTLoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setFilterProcessesUrl("/login");
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException { //사용자 인증처리
        UserLoginForm userLogin = objectMapper.readValue(request.getInputStream(), UserLoginForm.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword(), null);
        //user details..
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Users users = (Users) authResult.getPrincipal();
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer: "+ JWTUtil.makeAuthToken(users));
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getOutputStream().write(objectMapper.writeValueAsBytes(users));
    }
}
