package com.artifacts.game;

import com.artifacts.game.account.ValidateAndCreateCharacters;
import com.artifacts.game.launcher.GameLauncher;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SequenceOrchestrator {
    private final GameLauncher gameLauncher;
    private final ValidateAndCreateCharacters validateAndCreateCharacters;

    public SequenceOrchestrator(GameLauncher gameLauncher, ValidateAndCreateCharacters validateAndCreateCharacters) {
        this.gameLauncher = gameLauncher;
        this.validateAndCreateCharacters = validateAndCreateCharacters;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void execute() {
        // Step 1: Launch the game
        gameLauncher.gameStart();

        // Step 2:  Validate and Create characters
        validateAndCreateCharacters.createCharactersAtGameLaunchIfNotExist();
    }
}
