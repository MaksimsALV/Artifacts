package com.artifacts.api.service.character;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.CharactersApi;
import org.openapitools.client.model.CharacterResponseSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;

@Service
public class GetCharacter {
    private final ApiClient apiClient;
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public GetCharacter(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public ResponseEntity<CharacterResponseSchema> retrieveCharacter(MyCharacters character) {
        CharactersApi charactersApi = new CharactersApi(apiClient);

        while (true) {
            try {
                ResponseEntity<CharacterResponseSchema> response = charactersApi.getCharacterCharactersNameGetWithHttpInfo(character.getName());

                if (response.getStatusCode().value() == SUCCESS) {
                    return response;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Get Character");
                retry.retry();
            }
        }
    }
}
