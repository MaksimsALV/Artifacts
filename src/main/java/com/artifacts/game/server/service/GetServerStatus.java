package com.artifacts.game.server.service;

import org.openapitools.client.ApiClient;
import org.openapitools.client.api.ServerDetailsApi;
import org.openapitools.client.model.StatusResponseSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetServerStatus {
    private final ApiClient apiClient;

    @Autowired
    public GetServerStatus(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ResponseEntity<StatusResponseSchema> getServerStatus() {
        ServerDetailsApi serverDetailsApi = new ServerDetailsApi(apiClient);
        return serverDetailsApi.getServerDetailsGetWithHttpInfo();
    }

    public boolean serverIsUp() {
        return getServerStatus().getStatusCode() == HttpStatus.OK;
    }
}
