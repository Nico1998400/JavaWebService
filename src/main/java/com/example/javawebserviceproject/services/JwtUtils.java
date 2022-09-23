package com.example.javawebserviceproject.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Date;

@Service
public class JwtUtils {

    private final int EXPIRATION_MILLIS = 1000 * 60;

    public static KeyPair keyPair = getRSAKeys();


    @Value("${token.secret}")
    private String secret;

    public String generateToken(String username){

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(SignatureAlgorithm.RS512, keyPair.getPrivate())
                .compact();
    }

    public Boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(keyPair.getPublic()).parseClaimsJws(token);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private static KeyPair getRSAKeys() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public Claims parseBody(String token){
        return Jwts.parser().setSigningKey(keyPair.getPublic()).parseClaimsJws(token).getBody();
    }

}
