package com.artifacts.game;

import com.artifacts.api.caching.*;
import com.artifacts.api.service.account.GetBankItems;
import com.artifacts.api.service.mycharacters.GetMyCharacters;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.game.account.ValidateAndCreateCharacters;
import com.artifacts.game.launcher.GameLauncher;
import com.artifacts.game.resources.ValidateResourceStock;
import com.artifacts.game.resources.gathering.GatherMissingResource;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.GatheringSkill;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SequenceOrchestrator {
    private final GameLauncher gameLauncher;
    private final ValidateAndCreateCharacters validateAndCreateCharacters;
    private final GetMyCharacters getMyCharacters;
    private final GetBankItems getBankItems;
    private final GatherMissingResource gatherMissingResource;
    private final ValidateResourceStock validateResourceStock;
    private final CacheMaps cacheMaps;
    private final CacheResources cacheResources;
    private final CacheMonsters cacheMonsters;
    private final CacheItems cacheItems;
    private final CacheNpcs cacheNpcs;
    private final CacheNpcsItems cacheNpcsItems;

    // Phase 1: Game Launch, Validate and Create characters
    @EventListener(ApplicationReadyEvent.class)
    public void executePhase1() throws IOException {
        // Step 1: Launch the game
        gameLauncher.gameStart();

        // Step 2: Validate and Create characters
        validateAndCreateCharacters.createCharactersAtGameLaunchIfNotExist();

        System.out.println("Phase 1 Completed!");
        System.out.println("Executing Phase 2...");
        executePhase2();
    }

    // Phase 2: Get All My data (characters, banks, inventories)
    public void executePhase2() throws IOException {
        // Step 1: Get All My characters
        getMyCharacters.retrieveMyCharacters();

        // Step 2: Get Bank Items
        getBankItems.retrieveBankItems();
        System.out.println("Phase 2 Completed!");
        System.out.println("Executing Phase 3... Fetching Cache");
        executePhase3();
    }

    // Phase3: Caching
    public void executePhase3() throws IOException {
        cacheMaps.fetchAllMaps();
        cacheResources.fetchAllResources();
        cacheMonsters.fetchAllMonsters();
        cacheItems.fetchAllItems();
        cacheNpcs.fetchAllNpcs();
        cacheNpcsItems.fetchAllNpcsItems();
        System.out.println("Phase 3 Completed!");
        System.out.println("Executing Phase 4... Initial validation and checks");
        executePhase4();
    }

    public void executePhase4() {
        if (validateResourceStock.resourceStockHasMissingItems(GatheringSkill.MINING)) {
            gatherMissingResource.gatherMissingResource(MyCharacters.MINER, GatheringSkill.MINING);
        }
        if (validateResourceStock.resourceStockHasMissingItems(GatheringSkill.WOODCUTTING)) {
            gatherMissingResource.gatherMissingResource(MyCharacters.LUMBERJACK, GatheringSkill.WOODCUTTING);
        }
        if (validateResourceStock.resourceStockHasMissingItems(GatheringSkill.FISHING)) {
            gatherMissingResource.gatherMissingResource(MyCharacters.CHEF, GatheringSkill.FISHING);
        }
        if (validateResourceStock.resourceStockHasMissingItems(GatheringSkill.ALCHEMY)) {
            gatherMissingResource.gatherMissingResource(MyCharacters.ALCHEMIST, GatheringSkill.ALCHEMY);
        }
    }
}
