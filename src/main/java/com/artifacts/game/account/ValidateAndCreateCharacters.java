package com.artifacts.game.account;

import com.artifacts.api.service.account.GetAccountCharacters;
import com.artifacts.api.service.character.CreateCharacter;
import com.artifacts.game.Events;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ValidateAndCreateCharacters {
    private final GetAccountCharacters getAccountCharacters;
    private final CreateCharacter createCharacter;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ValidateAndCreateCharacters(GetAccountCharacters getAccountCharacters, CreateCharacter createCharacter, ApplicationEventPublisher applicationEventPublisher) {
        this.getAccountCharacters = getAccountCharacters;
        this.createCharacter = createCharacter;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener(Events.GameLaunchedSuccessfullyEvent.class)
    public void createCharactersAtGameLaunchIfNotExist() {
        var characterNames = getAccountCharacters.retrieveAccountCharacterNamesAsList();

        Arrays.stream(MyCharacters.values())
                .filter(character -> !characterNames.contains(character.getName()))
                .forEach(character -> createCharacter.createCharacter(character));
        applicationEventPublisher.publishEvent(new Events.AllCharactersAreReadyEvent());
    }
}
