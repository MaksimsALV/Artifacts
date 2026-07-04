package com.artifacts.game.account;

import com.artifacts.api.service.account.GetAccountCharacters;
import com.artifacts.api.service.character.CreateCharacter;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class ValidateAndCreateCharacters {
    private final GetAccountCharacters getAccountCharacters;
    private final CreateCharacter createCharacter;

    public ValidateAndCreateCharacters(GetAccountCharacters getAccountCharacters, CreateCharacter createCharacter) {
        this.getAccountCharacters = getAccountCharacters;
        this.createCharacter = createCharacter;
    }

    public void createCharactersAtGameLaunchIfNotExist() {
        var characterNames = getAccountCharacters.retrieveAccountCharacterNamesAsList();

        Arrays.stream(MyCharacters.values())
                .filter(character -> !characterNames.contains(character.getName()))
                .forEach(character -> createCharacter.createCharacter(character));
        System.out.println("All characters are ready");
    }
}
