package com.artifacts.api.service.account;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.TokenApi;
import org.openapitools.client.model.TokenResponseSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;

@Service
@RequiredArgsConstructor
public class GenerateToken {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(GenerateToken.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public void generateToken() {
        TokenApi tokenApi = new TokenApi(apiClient);

        while (true) {
            try {
                ResponseEntity<TokenResponseSchema> response = tokenApi.generateTokenTokenPostWithHttpInfo();
                TokenResponseSchema token = response.getBody();

                if (response.getStatusCode().value() == SUCCESS && token != null) {
                    apiClient.setBearerToken(token.getToken());
                    return;
                }
            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Generate Token");
                var responseHttpCode = e.getStatusCode().value();

                if (responseHttpCode == INVALID_PAYLOAD) {
                    return;
                } else if (responseHttpCode == TOKEN_GENERATION_FAIL) {
                    return;
                } else {
                    retry.retry();
                }
            }
        }
    }
}
