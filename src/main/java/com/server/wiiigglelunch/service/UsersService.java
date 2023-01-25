package com.server.wiiigglelunch.service;

import com.server.wiiigglelunch.configuration.JWTUtil;
import com.server.wiiigglelunch.configuration.VerifyResult;
import com.server.wiiigglelunch.domain.Photos.Photos;
import com.server.wiiigglelunch.domain.Users.*;
import com.server.wiiigglelunch.repository.PhotosRepository;
import com.server.wiiigglelunch.repository.UsersRepository;
import com.server.wiiigglelunch.security.SHA512PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Random;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PhotosRepository photosRepository;
    @Autowired
    JavaMailSender javaMailSender;
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
        Random rand = new Random(System.nanoTime());
        StringBuilder sb = new StringBuilder();
        // 총 20문자 길이의 난수를 생성
        for(int i=0; i<20; i++) {
            // 랜덤으로 true 또는 false 생성
            if(rand.nextBoolean()) {
                sb.append(rand.nextInt(10)); //0~9까지 난수 생성
            } else {
                sb.append((char)(rand.nextInt(26)+97)); //알파벳 난수 생성
            }
        }
        Users users = new Users();
        users.setNickname(usersSignUpForm.getNickname());
        users.setEmail(usersSignUpForm.getEmail());
        users.setEmailauth(0L);
        users.setPassword(usersSignUpForm.getPassword());
        Users newUsers = usersRepository.save(users);

        MimeMessage message = javaMailSender.createMimeMessage();

        try {

            message.setSubject("Wiiigle-lunch 메일 인증");
            String htmlContent = "<p>" + "Wiiigle-lunch 메일 인증" +"</p>"+"<p>아래의 링크를 클릭해주세요 !</p>" +
                    "<a href='https://localhost.ga/auth?email=" +
                    users.getEmail() +
                    "&token=" +
                    rand +
                    "'>Email 인증하기</a>";
            message.setText(htmlContent,"UTF-8","html");
            message.setFrom("Wiiigle-lunch@Wiiigle-lunch.com");
            //    message.setFrom(""); from 값을 설정하지 않으면 application.yml의 username값이 설정됩니다.
            message.addRecipients(Message.RecipientType.TO,users.getEmail());
            javaMailSender.send(message);
        }
        catch (MessagingException e){
            e.printStackTrace();
        }
        Photos photos = new Photos();
        photos.setUsersId(newUsers);
        photos.setSrc(usersSignUpForm.getImages());
        return newUsers;
    }
    @Transactional
    public Users login(UsersLoginForm usersLoginForm){
        SHA512PasswordEncoder encoder = new SHA512PasswordEncoder();
        Users users = usersRepository.findByEmail(usersLoginForm.getEmail());
        if (encoder.matches(usersLoginForm.getPassword(),users.getPassword())){
            return users;
        }
        return null;
    }
    @Transactional
    public UsersTokens tokenReissue(String accessToken, String refreshToken){
        VerifyResult verifyResultAccess = JWTUtil.verifyAccess(accessToken);
        VerifyResult verifyResultRefresh = JWTUtil.verifyRefresh(refreshToken);
        UsersTokens usersTokens = new UsersTokens();

        if (verifyResultRefresh.isSuccess() && verifyResultAccess.isSuccess()){
            System.out.println(verifyResultRefresh);
            System.out.println(verifyResultAccess);
            if (verifyResultRefresh.getUsername().equals(verifyResultAccess.getUsername())){
                Users users = usersRepository.findByEmail(verifyResultAccess.getUsername());
                usersTokens.setUsers(users);
                usersTokens.setAccessToken(JWTUtil.makeAuthToken(users));
                usersTokens.setRefreshToken(JWTUtil.makeRefreshToken(users));
                usersTokens.setVerified(true);
            }
            else{
                usersTokens.setVerified(false);
            }
        }
        else{
            usersTokens.setVerified(false);
        }

        return usersTokens;
    }
    @Transactional
    public Users oauthCreateOrLogin(UserOauthLoginForm userOauthLoginForm){
        Users users = usersRepository.findByEmail(userOauthLoginForm.getEmail());
        if (Objects.isNull(users)){
            Users newUser = new Users();
            newUser.setEmail(userOauthLoginForm.getEmail());
            newUser.setEmailauth(userOauthLoginForm.getEmailauth());// 구글 2 ,카카오3
            newUser.setNickname(userOauthLoginForm.getNickname());
            users = usersRepository.save(newUser);
        }
        else if (users.getEmailauth() != userOauthLoginForm.getEmailauth()){
            return null;
        }
        return users;
    }
    @Transactional
    public void checkEmail(String email, String salt){
        Users users = usersRepository.findByEmail(email);
        if (users.getSalt() == salt){
            users.setEmailauth(1L);
            usersRepository.save(users);
        }
    }

}
