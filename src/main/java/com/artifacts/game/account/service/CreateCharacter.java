package com.artifacts.game.account.service;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.tools.Retry;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.CharactersApi;
import org.openapitools.client.model.AddCharacterSchema;
import org.openapitools.client.model.CharacterResponseSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.artifacts.api.HttpCodes.*;
import static com.artifacts.game.account.Characters.WARRIOR;

@Service
public class CreateCharacter {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(CreateCharacter.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public CreateCharacter(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
    }

    public void createCharacters() {
        createCharacterWarrior();
    }

    public void createCharacterWarrior() {
        CharactersApi charactersApi = new CharactersApi(apiClient);
        AddCharacterSchema addCharacterSchema = new AddCharacterSchema();

        addCharacterSchema.setName(WARRIOR.getName());
        addCharacterSchema.setSkin(WARRIOR.getSkin());
        ResponseEntity<CharacterResponseSchema> response = charactersApi.createCharacterCharactersCreatePostWithHttpInfo(addCharacterSchema);

        while (response.getStatusCode() != SUCCESS) {
            apiResponseLogger.logErrorResponse(logger, response);

            var responseHttpCode = response.getStatusCode();
            if (responseHttpCode == INVALID_PAYLOAD) {
                return;
            } else if (responseHttpCode == CHARACTER_NAME_ALREADY_USED) {
                addCharacterSchema.setName(WARRIOR.getName() + "1");
            } else if (responseHttpCode == MAX_CHARACTERS_REACHED) {
                return;
            } else if (responseHttpCode == ACCOUNT_SKIN_NOT_OWNED) {
                return;
            }
            retry.retry();
            response = charactersApi.createCharacterCharactersCreatePostWithHttpInfo(addCharacterSchema);
        }
    }
}
