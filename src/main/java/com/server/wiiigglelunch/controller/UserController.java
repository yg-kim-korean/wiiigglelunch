package com.server.wiiigglelunch.controller;


import com.server.wiiigglelunch.domain.ResponseForm;
import com.server.wiiigglelunch.domain.Users.Users;
import com.server.wiiigglelunch.domain.Users.UsersSignUpForm;
import com.server.wiiigglelunch.service.UsersService;
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
        Users users = usersService.signUp(usersSignUpForm);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
