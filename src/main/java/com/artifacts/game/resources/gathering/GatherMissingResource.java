package com.artifacts.game.resources.gathering;

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
    private final ValidateResourceStock validateResourceStock;
    private final DestinationService destinationService;
    private final MovementService movementService;
    private final BankService bankService;
    private final GatheringService gatheringService;
    private final CharacterService characterService;

    @Async
    public void gatherMissingResource(MyCharacters character, GatheringSkill skill) {
        while (true) {
            var missingResourceCode = validateResourceStock.missingResourceCode(skill);

            if (missingResourceCode == null || !gatheringService.allowedToGather(character, missingResourceCode)) {
                //todo need better fallback here
                return;
            }

            movementService.moveToDestination(character, destinationService.destination(missingResourceCode));

            while (true) {
                var gather = gatheringService.gatherResource(character);

                if (gatheringService.fullInventory(gather)) {
                    movementService.moveToDestination(character, destinationService.destination("bank"));
                    bankService.depositItemsToBank(character, characterService.characterInventoryItems(character));
                    break;
                }
            }
        }
    }
}
