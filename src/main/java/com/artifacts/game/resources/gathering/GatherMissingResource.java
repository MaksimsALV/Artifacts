package com.artifacts.game.resources.gathering;

import com.artifacts.api.caching.CacheResources;
import com.artifacts.api.service.character.GetCharacter;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.game.resources.ValidateResourceStock;
import com.artifacts.game.service.*;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatherMissingResource {
    private final GetCharacter getCharacter;
    private final CacheResources cacheResources;
    private final ValidateResourceStock validateResourceStock;
    private final DestinationService destinationService;
    private final MovementService movementService;
    private final BankService bankService;
    private final GatheringService gatheringService;
    private final CharacterService characterService;

    @Async
    public void gatherMissingResource(MyCharacters character, GatheringSkill skill) {
        while (true) {
            //todo I thnk validator shouldnt be here. this whole class is for gatherning logic, not to validate what is missing
            var missingResourceCode = validateResourceStock.missingResourceCode(skill);

            if (missingResourceCode == null) {
                //todo need better fallback here
                return;
            }

            movementService.moveToDestination(character, destinationService.destination(missingResourceCode));

            while (true) {
                var gather = gatheringService.gather(character);

                if (gatheringService.fullInventory(gather)) {
                    movementService.moveToDestination(character, destinationService.destination("bank"));
                    bankService.depositItemsToBank(character, characterService.characterInventoryItems(character));
                    break;
                }
            }
        }
    }

    //todo this can go away once logic lives in ValidResourceStock
    private boolean validToGatherMiningResources(MyCharacters character, String missingResourceCode) {
        var characterData = getCharacter.retrieveCharacter(character).getBody().getData();
        var resourceData = cacheResources.getCachedResources().stream()
                .filter(resource -> resource.getCode().equals(missingResourceCode))
                .findFirst()
                .orElseThrow();
        return characterData.getMiningLevel() >= resourceData.getLevel();
    }

    //todo this can go away once logic lives in ValidResourceStock
    private boolean validToGatherHerbResources(MyCharacters character, String missingResourceCode) {
        var characterData = getCharacter.retrieveCharacter(character).getBody().getData();
        var resourceData = cacheResources.getCachedResources().stream()
                .filter(resource -> resource.getCode().equals(missingResourceCode))
                .findFirst()
                .orElseThrow();
        return characterData.getAlchemyLevel() >= resourceData.getLevel();
    }
}
