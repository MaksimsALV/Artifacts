package com.artifacts.game.account.service;

import com.artifacts.api.service.account.GetAccountCharacters;
import com.artifacts.api.service.character.CreateCharacter;
import com.artifacts.game.Events;
import com.artifacts.game.account.MyCharacters;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;

//todo tests
@Service
public class CharactersService {
    private final GetAccountCharacters getAccountCharacters;
    private final CreateCharacter createCharacter;

    public CharactersService(GetAccountCharacters getAccountCharacters, CreateCharacter createCharacter) {
        this.getAccountCharacters = getAccountCharacters;
        this.createCharacter = createCharacter;
    }

    @EventListener(Events.GameLaunchedSuccessfullyEvent.class)
    public void createCharactersAtGameLaunchIfNotExist() {
        var characterNames = getAccountCharacters.retrieveAccountCharacterNamesAsList();

        Arrays.stream(MyCharacters.values())
                .filter(character -> !characterNames.contains(character.getName()))
                .forEach(character -> createCharacter.createCharacter(character));
    }
}
