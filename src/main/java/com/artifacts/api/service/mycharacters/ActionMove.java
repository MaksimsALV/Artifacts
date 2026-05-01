package com.artifacts.api.service.mycharacters;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MyCharactersApi;
import org.openapitools.client.model.CharacterMovementDataSchema;
import org.openapitools.client.model.CharacterMovementResponseSchema;
import org.openapitools.client.model.DestinationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;

//todo tests
@Service
public class ActionMove {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(ActionMove.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ActionMove(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public ResponseEntity<CharacterMovementResponseSchema> move(MyCharacters character, DestinationSchema destination) {
        MyCharactersApi myCharactersApi = new MyCharactersApi(apiClient);

        while (true) {
            try {
                ResponseEntity<CharacterMovementResponseSchema> response = myCharactersApi.actionMoveMyNameActionMovePostWithHttpInfo(character.getName(), destination);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }
            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(logger, e);
                var responseHttpCode = e.getStatusCode().value();

                if (responseHttpCode == NOT_FOUND) {
                    throw e;
                } else if (responseHttpCode == INVALID_PAYLOAD) {
                    throw e;
                } else if (responseHttpCode == CHARACTER_LOCKED) {
                    throw e;
                } else if (responseHttpCode == CHARACTER_ALREADY_MAP) {
                    throw e;
                } else if (responseHttpCode == CHARACTER_CONDITION_NOT_MET) {
                    throw e;
                } else if (responseHttpCode == CHARACTER_NOT_FOUND) {
                    throw e;
                } else if (responseHttpCode == CHARACTER_IN_COOLDOWN) {
                    throw e;
                } else if (responseHttpCode == 595) {
                    throw e;
                } else if (responseHttpCode == 596) {
                    throw e;
                } else {
                    retry.retry();
                }
            }
        }
    }
}
