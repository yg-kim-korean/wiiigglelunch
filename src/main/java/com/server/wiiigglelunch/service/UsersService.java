package com.server.wiiigglelunch.service;

import com.server.wiiigglelunch.domain.Photos.Photos;
import com.server.wiiigglelunch.domain.Users.Users;
import com.server.wiiigglelunch.domain.Users.UsersSignUpForm;
import com.server.wiiigglelunch.repository.PhotosRepository;
import com.server.wiiigglelunch.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PhotosRepository photosRepository;
    public String checking(String email, String nickname){
        Users check_email = usersRepository.findByEmail(email);
        Users check_nickname = usersRepository.findByNickname(nickname);

        if( Objects.isNull(check_nickname) && Objects.isNull(check_email) ){
            return "no";
        }
        else if( !Objects.isNull(check_email)){
            return "email";
        }
        else if( !Objects.isNull(check_nickname)){
            return "nickname";
        }
        return null;
    }
    @Transactional
    public Users signUp(UsersSignUpForm usersSignUpForm){
        Users users = new Users();
        users.setNickname(usersSignUpForm.getNickname());
        users.setEmail(usersSignUpForm.getEmail());
        users.setSalt(0);
        users.setPassword(usersSignUpForm.getPassword());
        Users newUsers = usersRepository.save(users);
        Photos photos = new Photos();
        photos.setUsersId(newUsers);
        photos.setSrc(usersSignUpForm.getImages());
        return newUsers;
    }
}
