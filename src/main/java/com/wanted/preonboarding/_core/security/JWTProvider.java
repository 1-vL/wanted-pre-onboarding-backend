package com.wanted.preonboarding._core.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wanted.preonboarding._core.config.JWTSecret;
import com.wanted.preonboarding.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@RequiredArgsConstructor
public class JWTProvider {
    public static final Long EXP = 1000L * 60 * 60 * 48; // 48시간
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER = "Authorization";
    @Autowired
    public static JWTSecret JWTSecret;
    public static final String JWT_KEY = JWTSecret.jwt_key;

    public static String create(User user) {
        String jwt = JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXP))
                .withClaim("id", user.getId())
                .withClaim("role", user.getRoles())
                .sign(Algorithm.HMAC512(JWT_KEY));
        return TOKEN_PREFIX + jwt;
    }

    public static DecodedJWT verify(String jwt) throws SignatureVerificationException, TokenExpiredException {
        jwt = jwt.replace(JWTProvider.TOKEN_PREFIX, "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(JWT_KEY))
                .build().verify(jwt);
        return decodedJWT;
    }

}
