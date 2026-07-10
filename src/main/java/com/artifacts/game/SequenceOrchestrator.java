package com.artifacts.game;

import com.artifacts.api.service.account.GetBankItems;
import com.artifacts.api.service.mycharacters.GetMyCharacters;
import com.artifacts.game.account.ValidateAndCreateCharacters;
import com.artifacts.game.launcher.GameLauncher;
import com.artifacts.game.resources.Validate;
import com.artifacts.game.resources.mining.MiningResources;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SequenceOrchestrator {
    private final GameLauncher gameLauncher;
    private final ValidateAndCreateCharacters validateAndCreateCharacters;
    private final GetMyCharacters getMyCharacters;
    private final GetBankItems getBankItems;
    private final MiningResources miningResources;
    private final Validate validate;

    public SequenceOrchestrator(
            GameLauncher gameLauncher,
            ValidateAndCreateCharacters validateAndCreateCharacters,
            GetMyCharacters getMyCharacters,
            GetBankItems getBankItems,
            MiningResources miningResources,
            Validate validate) {
        this.gameLauncher = gameLauncher;
        this.validateAndCreateCharacters = validateAndCreateCharacters;
        this.getMyCharacters = getMyCharacters;
        this.getBankItems = getBankItems;
        this.miningResources = miningResources;
        this.validate = validate;
    }

    // Phase 1: Game Launch, Validate and Create characters
    @EventListener(ApplicationReadyEvent.class)
    public void executePhase1() {
        // Step 1: Launch the game
        gameLauncher.gameStart();

        // Step 2: Validate and Create characters
        validateAndCreateCharacters.createCharactersAtGameLaunchIfNotExist();

        System.out.println("Phase 1 Completed!");
        System.out.println("Executing Phase 2...");
        executePhase2();
    }

    // Phase 2: Get All My data (characters, banks, inventories)
    public void executePhase2() {
        // Step 1: Get All My characters
        getMyCharacters.retrieveMyCharacters();

        // Step 2: Get Bank Items
        getBankItems.retrieveBankItems();
        System.out.println("Phase 2 Completed!");
        System.out.println("Executing Phase 3...");
        executePhase3();
    }

    public void executePhase3() {
        //todo if validateresoursestockistrue then gatherminingresource (need to convert to boolean)
        validate.validateResourceStock();
        miningResources.gatherMiningResource();
    }
}
