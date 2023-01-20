package com.server.wiiigglelunch.controller;


import com.server.wiiigglelunch.domain.ResponseForm;
import com.server.wiiigglelunch.domain.Users.LoginResultForm;
import com.server.wiiigglelunch.domain.Users.Users;
import com.server.wiiigglelunch.domain.Users.UsersLoginForm;
import com.server.wiiigglelunch.domain.Users.UsersSignUpForm;
import com.server.wiiigglelunch.service.UsersService;
import com.server.wiiigglelunch.security.SHA512PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    private ResponseEntity<?> login(@RequestBody UsersLoginForm usersLoginForm){
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
        return new ResponseEntity<>(users,HttpStatus.OK);

    }

}
