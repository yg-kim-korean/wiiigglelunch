package com.server.wiiigglelunch.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersSignUpForm {
    private String email;
    private String nickname;
    private String password;
    private String images;
}
