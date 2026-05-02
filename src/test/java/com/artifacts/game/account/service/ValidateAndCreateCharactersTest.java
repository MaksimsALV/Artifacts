package com.artifacts.game.account.service;

import com.artifacts.api.service.account.GetAccountCharacters;
import com.artifacts.api.service.character.CreateCharacter;
import com.artifacts.game.Events;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.game.account.ValidateAndCreateCharacters;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

class ValidateAndCreateCharactersTest {

    @Test
    void allCharactersShouldBeReady_whenFlowEnds() {
        GetAccountCharacters getAccountCharacters = mock(GetAccountCharacters.class);
        CreateCharacter createCharacter = mock(CreateCharacter.class);
        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);

        List<String> characterNames = Arrays.stream(MyCharacters.values())
                .map(MyCharacters::getName)
                .toList();

        when(getAccountCharacters.retrieveAccountCharacterNamesAsList())
                .thenReturn(characterNames);

        ValidateAndCreateCharacters validateAndCreateCharacters =
                new ValidateAndCreateCharacters(getAccountCharacters, createCharacter, applicationEventPublisher);

        validateAndCreateCharacters.createCharactersAtGameLaunchIfNotExist();

        verify(applicationEventPublisher)
                .publishEvent(any(Events.AllCharactersAreReadyEvent.class));
    }

    @Test
    void shouldNotCreateCharacters_whenAllCharactersAlreadyExist() {
        GetAccountCharacters getAccountCharacters = mock(GetAccountCharacters.class);
        CreateCharacter createCharacter = mock(CreateCharacter.class);
        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);

        List<String> characterNames = Arrays.stream(MyCharacters.values())
                .map(MyCharacters::getName)
                .collect(Collectors.toList());

        when(getAccountCharacters.retrieveAccountCharacterNamesAsList())
                .thenReturn(characterNames);

        ValidateAndCreateCharacters validateAndCreateCharacters =
                new ValidateAndCreateCharacters(getAccountCharacters, createCharacter, applicationEventPublisher);

        validateAndCreateCharacters.createCharactersAtGameLaunchIfNotExist();

        verify(createCharacter, never()).createCharacter(any(MyCharacters.class));
    }

    @Test
    void shouldCreateMaxOnly_whenMaxDoesNotExist() {
        GetAccountCharacters getAccountCharacters = mock(GetAccountCharacters.class);
        CreateCharacter createCharacter = mock(CreateCharacter.class);
        ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);

        MyCharacters maxCharacter = Arrays.stream(MyCharacters.values())
                .filter(character -> character.getName().equals("Max"))
                .findFirst()
                .get();

        List<String> characterNames = Arrays.stream(MyCharacters.values())
                .map(MyCharacters::getName)
                .filter(name -> !name.equals("Max"))
                .collect(Collectors.toList());

        when(getAccountCharacters.retrieveAccountCharacterNamesAsList())
                .thenReturn(characterNames);

        ValidateAndCreateCharacters validateAndCreateCharacters =
                new ValidateAndCreateCharacters(getAccountCharacters, createCharacter, applicationEventPublisher);

        validateAndCreateCharacters.createCharactersAtGameLaunchIfNotExist();

        verify(createCharacter, times(1)).createCharacter(maxCharacter);
        verifyNoMoreInteractions(createCharacter);
    }
}