package com.wanted.preonboarding._core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class JWTSecret {
    public static String jwt_key = "";

    @Value("${jwt.secret}")
    public void setJWTKey(String value) {
        jwt_key = value;
    }

}