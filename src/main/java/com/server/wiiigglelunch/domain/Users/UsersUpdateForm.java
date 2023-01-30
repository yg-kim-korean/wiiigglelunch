package com.server.wiiigglelunch.domain.Users;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UsersUpdateForm {
    private String nickname;
    private String password;
    private String email;

}
