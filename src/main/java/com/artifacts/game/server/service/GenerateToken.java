package com.artifacts.game.server.service;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.TokenApi;
import org.openapitools.client.model.TokenResponseSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class GenerateToken {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(GenerateToken.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public GenerateToken(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public void generateToken() {
        TokenApi tokenApi = new TokenApi(apiClient);
        ResponseEntity<TokenResponseSchema> response = tokenApi.generateTokenTokenPostWithHttpInfo();
        TokenResponseSchema token = response.getBody();

        while (response.getStatusCode() != HttpStatus.OK || token == null) {
            apiResponseLogger.logErrorResponse(logger, response);
            retry.retry();
            response = tokenApi.generateTokenTokenPostWithHttpInfo();
            token = response.getBody();
        }

        apiClient.setBearerToken(token.getToken());
    }
}
