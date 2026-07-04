package com.artifacts.api.service;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.ServerDetailsApi;
import org.openapitools.client.model.StatusResponseSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;

@Service
public class GetServerStatus {
    private final ApiClient apiClient;
    private final Logger logger =  LoggerFactory.getLogger(GetServerStatus.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public GetServerStatus(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public ResponseEntity<StatusResponseSchema> getServerStatus() {
        ServerDetailsApi serverDetailsApi = new ServerDetailsApi(apiClient);

        while (true) {
            try {
                ResponseEntity<StatusResponseSchema> response = serverDetailsApi.getServerDetailsGetWithHttpInfo();

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }
            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get Server Status");
                retry.retry();
            }
        }
    }

    public boolean serverIsUp() {
        return getServerStatus().getStatusCode().value() == SUCCESS;
    }
}
