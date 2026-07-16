package com.artifacts.api.service.account;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MyAccountApi;
import org.openapitools.client.model.MyAccountDetailsSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;

@Service
@RequiredArgsConstructor
public class GetAccountDetails {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(GetAccountDetails.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<MyAccountDetailsSchema> retrieveAccountDetails() {
        MyAccountApi myAccountsApi = new MyAccountApi(apiClient);

        while (true) {
            try {
                ResponseEntity<MyAccountDetailsSchema> response = myAccountsApi.getAccountDetailsMyDetailsGetWithHttpInfo();

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get Account Details");
                retry.retry();
            }
        }
    }

    public String retrieveAccountUsername() {
        return retrieveAccountDetails().getBody().getData().getUsername();
    }
}
