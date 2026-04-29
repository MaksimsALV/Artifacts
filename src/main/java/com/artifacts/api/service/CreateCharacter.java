package com.artifacts.api.service;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.game.Events;
import com.artifacts.game.account.Characters;
import com.artifacts.tools.Retry;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.CharactersApi;
import org.openapitools.client.model.AddCharacterSchema;
import org.openapitools.client.model.CharacterResponseSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;
import static com.artifacts.game.account.Characters.*;

@Service
public class CreateCharacter {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(CreateCharacter.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;
    private final GetAccountCharacters getAccountCharacters;
    private final GetAccountDetails getAccountDetails;

    public CreateCharacter(ApiClient apiClient, ApiResponseLogger apiResponseLogger, Retry retry, GetAccountCharacters getAccountCharacters, GetAccountDetails getAccountDetails) {
        this.apiClient = apiClient;
        this.apiResponseLogger = apiResponseLogger;
        this.retry = retry;
        this.getAccountCharacters = getAccountCharacters;
        this.getAccountDetails = getAccountDetails;
    }

    @EventListener(Events.GameLaunchedSuccessfullyEvent.class)
    public void createCharacters() throws JsonProcessingException {
        //todo continue with the logic to check if chars with names already exist, and if yes, then skip this step.
        getAccountCharacters.retrieveAccountCharacters();

        createCharacter(WARRIOR);
        createCharacter(MINER);
        createCharacter(LUMBERJACK);
        createCharacter(CHEF);
        createCharacter(ALCHEMIST);
    }

    public void createCharacter(Characters character) {
        CharactersApi charactersApi = new CharactersApi(apiClient);
        AddCharacterSchema addCharacterSchema = new AddCharacterSchema();

        addCharacterSchema.setName(character.getName());
        addCharacterSchema.setSkin(character.getSkin());

        var nameSuffix = 1;

        while (true) {
            try {
                ResponseEntity<CharacterResponseSchema> response = charactersApi.createCharacterCharactersCreatePostWithHttpInfo(addCharacterSchema);

                if (response.getStatusCode().value() == SUCCESS) {
                    return;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(logger, e);
                var responseHttpCode = e.getStatusCode().value();

                if (responseHttpCode == INVALID_PAYLOAD) {
                    return;
                } else if (responseHttpCode == CHARACTER_NAME_ALREADY_USED) {
                    addCharacterSchema.setName(WARRIOR.getName() + nameSuffix);
                    nameSuffix++;
                } else if (responseHttpCode == MAX_CHARACTERS_REACHED) {
                    return;
                } else if (responseHttpCode == ACCOUNT_SKIN_NOT_OWNED) {
                    return;
                } else {
                    retry.retry();
                }
            }
        }
    }
}
