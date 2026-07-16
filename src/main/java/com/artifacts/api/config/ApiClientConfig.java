package com.artifacts.api.config;

import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfig {
    private final Properties properties;

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(properties.getBaseUrl());
        apiClient.setUsername(properties.getUsername());
        apiClient.setPassword(properties.getPassword());
        return apiClient;
    }
}
