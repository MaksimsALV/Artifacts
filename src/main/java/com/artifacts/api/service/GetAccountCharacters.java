package com.artifacts.api.service;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.AccountsApi;
import org.openapitools.client.model.CharactersListSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;

//todo tests
@Service
public class GetAccountCharacters {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(GetAccountCharacters.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;
    private final GetAccountDetails getAccountDetails;

    public GetAccountCharacters(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry, GetAccountDetails getAccountDetails) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
        this.getAccountDetails = getAccountDetails;
    }

    public ResponseEntity<CharactersListSchema> retrieveAccountCharacters() {
        AccountsApi accountsApi = new AccountsApi(apiClient);

        while (true) {
            try {
                ResponseEntity<CharactersListSchema> response = accountsApi.getAccountCharactersAccountsAccountCharactersGetWithHttpInfo(getAccountDetails.retrieveAccountUsername());

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }
            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(logger, e);
                retry.retry();
            }
        }
    }
}
