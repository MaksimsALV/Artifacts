package com.artifacts.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Auth {
    @Value("${api.username}")
    private String getUsername;

    public String getUsername() {
        return getUsername;
    }

    @Value("${api.password}")
    private String getPassword;

    public String getPassword() {
        return getPassword;
    }
}
