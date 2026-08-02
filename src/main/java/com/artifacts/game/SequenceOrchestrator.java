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
    private final CacheTasks cacheTasks;
    private final CacheMyCharacters cacheMyCharacters;
    private final CacheBankItems cacheBankItems;

    @EventListener(ApplicationReadyEvent.class)
    public void executePhase1() throws IOException {
        gameLauncher.gameStart();
        validateAndCreateCharacters.createCharactersAtGameLaunchIfNotExist();

        System.out.println("Phase 1 Completed!");
        System.out.println("Executing Phase 2... Fetching data");
        executePhase2();
    }

    public void executePhase2() throws IOException {
        cacheMaps.fetchAllMaps();
        cacheResources.fetchAllResources();
        cacheMonsters.fetchAllMonsters();
        cacheItems.fetchAllItems();
        cacheNpcs.fetchAllNpcs();
        cacheNpcsItems.fetchAllNpcsItems();
        cacheTasks.fetchAllTasks();
        cacheMyCharacters.fetchAllCharacters();
        cacheBankItems.fetchAllBankItems();
        System.out.println("Phase 2 Completed!");
        System.out.println("Executing Phase 3... Initial validation and checks\"");
        executePhase3();
    }

    public void executePhase3() {
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
