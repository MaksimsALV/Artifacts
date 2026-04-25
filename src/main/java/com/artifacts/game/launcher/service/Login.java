package com.artifacts.game.launcher.service;

import org.openapitools.client.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Login {
    private final ApiClient apiClient;

    @Autowired
    public Login(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void login() {
        //
    }
}
