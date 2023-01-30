package com.server.wiiigglelunch.controller;


import com.server.wiiigglelunch.configuration.JWTUtil;
import com.server.wiiigglelunch.domain.ResponseForm;
import com.server.wiiigglelunch.domain.Users.*;
import com.server.wiiigglelunch.service.UsersService;
import com.server.wiiigglelunch.security.SHA512PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UsersService usersService;

    @PostMapping("/signup")
    private ResponseEntity<?> signUp(@RequestBody UsersSignUpForm usersSignUpForm){
        ResponseForm responseForm = new ResponseForm();
        SHA512PasswordEncoder encoder = new SHA512PasswordEncoder();
        String check = usersService.checking(usersSignUpForm.getEmail(),usersSignUpForm.getNickname());
        if (Objects.equals(check,"email")){
            responseForm.setMessage("이미 존재하는 이메일입니다.");
            responseForm.setNickName("false");
            return new ResponseEntity<>(responseForm, HttpStatus.CONFLICT);
        }
        else if(Objects.equals(check,"nickname")) {
            responseForm.setMessage("이미 존재하는 닉네임입니다.");
            responseForm.setNickName("false");
            return new ResponseEntity<>(responseForm, HttpStatus.CONFLICT);
        }
        usersSignUpForm.setPassword(encoder.encode(usersSignUpForm.getPassword()));
        Users users = usersService.signUp(usersSignUpForm);
        if(!Objects.isNull(users)){
            return new ResponseEntity<>(users, HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }
    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody UsersLoginForm usersLoginForm, HttpServletResponse response){
        ResponseForm responseForm  = new ResponseForm();
        LoginResultForm loginResultForm = new LoginResultForm();
        if (Objects.isNull(usersLoginForm.getEmail())){
            responseForm.setMessage("아이디를 넣어주십시오");
            return new ResponseEntity<>(responseForm, HttpStatus.CONFLICT);
        }
        else if(Objects.isNull(usersLoginForm.getPassword())){
            responseForm.setMessage("비밀번호를 넣어주십시오");
            return new ResponseEntity<>(responseForm, HttpStatus.CONFLICT);
        }
        Users users = usersService.login(usersLoginForm);
        if (Objects.isNull(users)){
            responseForm.setMessage("로그인 : 일치하는 유저 정보가 없습니다.");
            return new ResponseEntity<>(responseForm,HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8 ));
        String accessToken = JWTUtil.makeAuthToken(users);
        String refToken = JWTUtil.makeRefreshToken(users);
        headers.set("authentication", "bearer "+ accessToken);
        Cookie cookie = new Cookie("refreshToken",refToken);
        response.addCookie(cookie);
        loginResultForm.setUsers(users);
        loginResultForm.setAccessToken("bearer "+  accessToken);
        return new ResponseEntity<>(loginResultForm,headers, HttpStatus.OK);

    }
    @GetMapping("/users") // 토큰 재발급
    public ResponseEntity<?> tokenReissue(HttpServletRequest request, HttpServletResponse response) {
        ResponseForm responseForm = new ResponseForm();
        Cookie[] cookie;
        String accessToken;
        String refreshToken = "";
        Users users;

        try {
            accessToken = request.getHeader("authentication");

        } catch (Exception e) {
            responseForm.setMessage("토큰 재발급 : 로그인이 만료되었습니다.(accessToken)");
            return new ResponseEntity<>(responseForm, HttpStatus.UNAUTHORIZED);
        }
        try {
            cookie = request.getCookies();

        } catch (Exception e) {
            responseForm.setMessage("토큰 재발급 : 로그인이 만료되었습니다.(refreshToken)");
            return new ResponseEntity<>(responseForm, HttpStatus.UNAUTHORIZED);
        }
        for (Cookie value : cookie) {

            if (value.getName().equals("refreshToken")) {
                refreshToken = value.getValue();
            }
            if (refreshToken.isEmpty()) {
                responseForm.setMessage("토큰 재발급 : 로그인이 만료되었습니다.(refreshToken)");
                return new ResponseEntity<>(responseForm, HttpStatus.UNAUTHORIZED);
            }
        }
        UsersTokens usersTokens = usersService.tokenReissue(accessToken, refreshToken);
        HttpHeaders headers = new HttpHeaders();
        System.out.println(usersTokens);
        if (usersTokens.getVerified()) {
            users = usersTokens.getUsers();

            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

            headers.set("authentication", "bearer " + usersTokens.getAccessToken());
            Cookie cookies = new Cookie("refreshToken", usersTokens.getRefreshToken());
            response.addCookie(cookies);
        } else {
            responseForm.setMessage("토큰 재발급 : 일치하는 유저 정보가 없습니다.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(users, headers, HttpStatus.OK);
    }
    @PostMapping("/oauth")
    public ResponseEntity<?> oauth(@RequestBody UserOauthLoginForm userOauthLoginForm,HttpServletResponse response) {
        ResponseForm responseForm = new ResponseForm();
        Users users = usersService.oauthCreateOrLogin(userOauthLoginForm);
        if (Objects.isNull(users)){
            responseForm.setMessage("Oauth 로그인 : 일치하는 유저 정보가 없습니다.");
            return new ResponseEntity<>(responseForm,HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8 ));
        String accessToken = JWTUtil.makeAuthToken(users);
        String refToken = JWTUtil.makeRefreshToken(users);
        headers.set("authentication", "bearer "+ accessToken);
        Cookie cookie = new Cookie("refreshToken",refToken);
        response.addCookie(cookie);
        return new ResponseEntity<>(users,headers,HttpStatus.OK);
    }
    @GetMapping("/auth") //email 인증
    public ResponseEntity<?> email_auth(@RequestParam("email") String email, @RequestParam("token") String token){
        ResponseForm responseForm = new ResponseForm();
        usersService.checkEmail(email,token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/users")
    public ResponseEntity<?> update_Users(@RequestBody UsersUpdateForm usersUpdateForm, HttpServletRequest request, HttpServletResponse response){
        ResponseForm responseForm = new ResponseForm();
        String accessToken;
        try {
            accessToken = request.getHeader("authentication");

        } catch (Exception e) {
            responseForm.setMessage("토큰 재발급 : 로그인이 만료되었습니다.(accessToken)");
            return new ResponseEntity<>(responseForm, HttpStatus.UNAUTHORIZED);
        }
        Users users = usersService.update(accessToken,usersUpdateForm);
        if (Objects.isNull(users)){
            responseForm.setMessage("업데이트 실패 해당 메일로 가입한 사람이 없습니다.");
            return new ResponseEntity<>(responseForm,HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

}
