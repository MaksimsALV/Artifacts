package com.artifacts.api.service.mycharacters;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.MyCharactersApi;
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
@RequiredArgsConstructor
public class ActionMove {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(ActionMove.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public ResponseEntity<CharacterMovementResponseSchema> move(MyCharacters character, DestinationSchema destination) {
        MyCharactersApi myCharactersApi = new MyCharactersApi(apiClient);

        while (true) {
            try {
                ResponseEntity<CharacterMovementResponseSchema> response = myCharactersApi.actionMoveMyNameActionMovePostWithHttpInfo(character.getName(), destination);

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }
            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Action Move");
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
