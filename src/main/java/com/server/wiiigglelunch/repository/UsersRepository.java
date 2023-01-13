package com.server.wiiigglelunch.repository;

import com.server.wiiigglelunch.domain.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Users findByEmail(String email);
    Users findByNickname(String nickname);
}
