package com.server.wiiigglelunch.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.server.wiiigglelunch.domain.Users.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
@Component
public class JWTUtil {

    private static String secret_key;
    private static String refresh_key;
    private static final long AUTH_TIME = 20*60; //60초
    private static final long REFRESH_TIME = 24*60*60*7; //일주일

    @Value("${data.refresh-key}")
    public void setRefresh_key(String refresh_key) {
        JWTUtil.refresh_key = refresh_key;
    }
    @Value("${data.secret-key}")
    public void setSecret_key(String secret_key) {
        JWTUtil.secret_key = secret_key;
    }
    public static String makeAuthToken(Users users){

        return JWT.create().withSubject(users.getEmail())
                .withClaim("exp", Instant.now().getEpochSecond()+AUTH_TIME)
                .sign(Algorithm.HMAC256(secret_key));
    }
    public static String makeRefreshToken(Users users){

        return JWT.create().withSubject(users.getEmail())
                .withClaim("exp", Instant.now().getEpochSecond()+REFRESH_TIME)
                .sign(Algorithm.HMAC256(refresh_key));
    }

    public static VerifyResult verifyAccess(String token){
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(secret_key)).build().verify(token.split(" ")[1]);
            return VerifyResult.builder().success(true).username(verify.getSubject()).build();
        }catch (Exception e){
            DecodedJWT decode = JWT.decode(token);
            return VerifyResult.builder().success(false).username(decode.getSubject()).build();
        }

    }
    public static VerifyResult verifyRefresh(String token){
        try {
            DecodedJWT verify = JWT.require(Algorithm.HMAC256(refresh_key)).build().verify(token);
            return VerifyResult.builder().success(true).username(verify.getSubject()).build();
        }catch (Exception e){
            DecodedJWT decode = JWT.decode(token);
            return VerifyResult.builder().success(false).username(decode.getSubject()).build();
        }

    }

}
