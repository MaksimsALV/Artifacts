package com.artifacts.game.server.service;

import com.artifacts.api.logs.ApiResponseLogger;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.ServerDetailsApi;
import org.openapitools.client.model.StatusResponseSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GetServerStatus {
    private final ApiClient apiClient;
    private final Logger logger =  LoggerFactory.getLogger(GetServerStatus.class);
    private final ApiResponseLogger apiResponseLogger;

    public GetServerStatus(ApiClient apiClient, ApiResponseLogger apiResponseLogger) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
    }

    public ResponseEntity<StatusResponseSchema> getServerStatus() {
        ServerDetailsApi serverDetailsApi = new ServerDetailsApi(apiClient);
        ResponseEntity<StatusResponseSchema> response = serverDetailsApi.getServerDetailsGetWithHttpInfo();

        apiResponseLogger.logResponseOnError(logger, response);

        return response;
    }

    public boolean serverIsUp() {
        return getServerStatus().getStatusCode() == HttpStatus.OK;
    }
}
