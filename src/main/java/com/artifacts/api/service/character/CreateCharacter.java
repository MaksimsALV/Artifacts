package com.artifacts.api.service.character;

import com.artifacts.api.logs.ApiResponseLogger;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Retry;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.ApiClient;
import org.openapitools.client.api.CharactersApi;
import org.openapitools.client.model.AddCharacterSchema;
import org.openapitools.client.model.CharacterResponseSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import static com.artifacts.api.HttpCodes.*;

@Service
@RequiredArgsConstructor
public class CreateCharacter {
    private final ApiClient apiClient;
    private final Logger logger = LoggerFactory.getLogger(CreateCharacter.class);
    private final ApiResponseLogger apiResponseLogger;
    private final Retry retry;

    public void createCharacter(MyCharacters character) {
        CharactersApi charactersApi = new CharactersApi(apiClient);
        AddCharacterSchema addCharacterSchema = new AddCharacterSchema();

        addCharacterSchema.setName(character.getName());
        addCharacterSchema.setSkin(character.getSkin());

        while (true) {
            try {
                ResponseEntity<CharacterResponseSchema> response = charactersApi.createCharacterCharactersCreatePostWithHttpInfo(addCharacterSchema);

                if (response.getStatusCode().value() == SUCCESS) {
                    return;
                }

            } catch (RestClientResponseException e) {
                apiResponseLogger.logErrorResponse(e, "Create Character");
                var responseHttpCode = e.getStatusCode().value();

                if (responseHttpCode == INVALID_PAYLOAD) {
                    return;
                } else if (responseHttpCode == CHARACTER_NAME_ALREADY_USED) {
                    return;
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
