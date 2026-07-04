package com.artifacts.api.service.mycharacters;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MyCharactersApi;
import org.openapitools.client.model.CharacterResponseSchema;
import org.openapitools.client.model.MyCharactersListSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
public class GetMyCharacters {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(GetMyCharacters.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public GetMyCharacters(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public ResponseEntity<MyCharactersListSchema> retrieveMyCharacters() {
        MyCharactersApi myCharactersApi = new MyCharactersApi(apiClient);

        while (true) {
            try {
                ResponseEntity<MyCharactersListSchema> response = myCharactersApi.getMyCharactersMyCharactersGetWithHttpInfo();

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get My Characters");
                retry.retry();
            }
        }
    }
}
