package com.artifacts.api.config;

import org.openapitools.client.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiClientConfig {
    private final Properties properties;

    public ApiClientConfig(Properties properties) {
        this.properties = properties;
    }

    @Bean
    public ApiClient apiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(properties.getBaseUrl());
        apiClient.setUsername(properties.getUsername());
        apiClient.setPassword(properties.getPassword());
        return apiClient;
    }
}
