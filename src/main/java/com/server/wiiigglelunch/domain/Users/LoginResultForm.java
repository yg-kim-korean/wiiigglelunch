package com.server.wiiigglelunch.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResultForm {
    private String accessToken;
    private Users users;
}

