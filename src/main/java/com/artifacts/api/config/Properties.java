package com.artifacts.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Properties {

    @Value("${api.baseUrl}")
    private String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
    }

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
