package com.artifacts.api.service.mycharacters;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MyCharactersApi;
import org.openapitools.client.model.CharacterTransitionResponseSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;

@Service
public class ActionTransition {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ActionTransition(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public ResponseEntity<CharacterTransitionResponseSchema> transition(MyCharacters character) {
        MyCharactersApi myCharactersApi = new MyCharactersApi(apiClient);

        while (true) {
            try {
                ResponseEntity<CharacterTransitionResponseSchema> response = myCharactersApi.actionTransitionMyNameActionTransitionPostWithHttpInfo(character.getName());

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }
            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Action Transition");
                var responseHttpCode = e.getStatusCode().value();

                if (responseHttpCode == NOT_FOUND ||
                        responseHttpCode == INVALID_PAYLOAD ||
                        responseHttpCode == CHARACTER_LOCKED ||
                        responseHttpCode == CHARACTER_ALREADY_MAP ||
                        responseHttpCode == CHARACTER_CONDITION_NOT_MET ||
                        responseHttpCode == CHARACTER_NOT_FOUND ||
                        responseHttpCode == CHARACTER_IN_COOLDOWN ||
                        responseHttpCode == NO_PATH_AVAILABLE_TO_THE_DESTINATION_MAP ||
                        responseHttpCode == THE_MAP_IS_BLOCKED_AND_CANNOT_BE_ACCESSED) {

                    return ResponseEntity.status(responseHttpCode).build();
                } else {
                    retry.retry();
                }
            }
        }
    }
}
