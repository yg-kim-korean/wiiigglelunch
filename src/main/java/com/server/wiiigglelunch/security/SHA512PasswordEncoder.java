package com.server.wiiigglelunch.security;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;

public class SHA512PasswordEncoder implements PasswordEncoder {
    private final Log logger = LogFactory.getLog(getClass());
    @Override
    public String encode(CharSequence rawPassword) {
        if (rawPassword == null){
            throw new IllegalArgumentException("빈칸은 넣으면 안됨");
        }
        return this.getSHA512Pw(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("비밀번호에 빈칸 넣지마");
        }
        if (encodedPassword == null || encodedPassword.length() == 0) {
            this.logger.warn("빈칸 넣지말라고");
            return false;
        }
        String encodedRawPw = this.getSHA512Pw(rawPassword);
        if (encodedPassword.length() != encodedRawPw.length()) {
            return false;
        }
        for (int i = 0; i < encodedPassword.length(); i++) {
            if(encodedPassword.charAt(i) != encodedRawPw.charAt(i)) return false;
        }
        return true;
    }
    private String getSHA512Pw(CharSequence rawPassword){
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(rawPassword.toString().getBytes());
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
        }
        byte[] msgb = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< msgb.length; i++){
            String tmp = Integer.toHexString(msgb[i] & 0xFF);
            while (tmp.length()<2){
                tmp = "0"+tmp;
            }
            sb.append(tmp.substring(tmp.length()-2));
        }
        return sb.toString();
    }


}
