package com.artifacts.game.resources.gathering;

import com.artifacts.api.service.character.GetCharacter;
import com.artifacts.api.service.mycharacters.ActionDepositBankItem;
import com.artifacts.api.service.mycharacters.ActionGathering;
import com.artifacts.api.service.mycharacters.ActionMove;
import com.artifacts.api.service.mycharacters.ActionTransition;
import com.artifacts.api.service.resources.GetResource;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.game.resources.ValidateResourceStock;
import com.artifacts.tools.Sleep;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.artifacts.api.HttpCodes.CHARACTER_INVENTORY_FULL;

@Service
@RequiredArgsConstructor
public class GatherMissingResource {
    private final GetCharacter getCharacter;
    private final GetResource getResource;
    private final ActionMove actionMove;
    private final ActionGathering actionGathering;
    private final ActionDepositBankItem actionDepositBankItem;
    private final ActionTransition actionTransition;
    private final Sleep sleep;
    private final ValidateResourceStock validateResourceStock;
    private final LocationService locationService;

    private static final Set<String> EXCEPTIONAL_RESOURCE_CODES = Set.of(
            "gold_rocks",
            "mithril_rocks",
            "nettle"
    );

    @Async
    public void gatherMissingResource(MyCharacters character, GatheringSkill skill) {
        while (true) {
            var missingResourceCode = validateResourceStock.missingResourceCode(skill);

            if (missingResourceCode == null) {
                //todo need better fallback here
                return;
            }

            if (EXCEPTIONAL_RESOURCE_CODES.contains(missingResourceCode)) {
                exceptionalResourceHandling(character, missingResourceCode);

            } else {
                moveToDestination(character, locationService.destination(missingResourceCode));
            }

            while (true) {
                var gather = gather(character);

                if (inventoryIsFull(gather)) {
                    //todo need to somehow get out of the mine to forest
                    var bankLocation = locationService.destination("bank");
                    moveToDestination(character, bankLocation);
                    var itemsDepositPayload = retrieveItemsFromCharacterInventoryAsList(character);
                    depositItemsToBank(character, itemsDepositPayload);
                    break;
                }
            }
        }
    }

    private List<SimpleItemSchema> retrieveItemsFromCharacterInventoryAsList(MyCharacters character) {
        var characterData = getCharacter.retrieveCharacter(character);
        return characterData.getBody().getData().getInventory().stream()
                .filter(nonEmptyItem -> nonEmptyItem.getQuantity() > 0)
                .map(item -> {
                    SimpleItemSchema payload = new SimpleItemSchema();
                    payload.setCode(item.getCode());
                    payload.setQuantity(item.getQuantity());
                    return payload;
                })
                .toList();
    }

    private void exceptionalResourceHandling(MyCharacters character, String missingResourceCode) {
        if ("gold_rocks".equals(missingResourceCode)) {
            //todo this can go away once logic lives in ValidResourceStock
            if (!validToGatherMiningResources(character, missingResourceCode)) {
                //todo need better fallback here
                return;
            }
            moveToDestination(character, locationService.entranceToGoldMineLocation());
            transitionToAnotherLayer(character);
            moveToDestination(character, locationService.destination(missingResourceCode));

        } else if ("mithril_rocks".equals(missingResourceCode)) {
            //todo this can go away once logic lives in ValidResourceStock
            if (!validToGatherMiningResources(character, missingResourceCode)) {
                //todo need better fallback here
                return;
            }
            moveToDestination(character, locationService.entranceToMithrilMineLocation());
            transitionToAnotherLayer(character);
            moveToDestination(character, locationService.destination(missingResourceCode));

        } else if ("nettle".equals(missingResourceCode)) {
            //todo this can go away once logic lives in ValidResourceStock
            if (!validToGatherHerbResources(character, missingResourceCode)) {
                //todo need better fallback here
                return;
            }
            moveToDestination(character, locationService.destination(missingResourceCode));
        }
    }

    //todo this can go away once logic lives in ValidResourceStock
    private boolean validToGatherMiningResources(MyCharacters character, String missingResourceCode) {
        var characterData = getCharacter.retrieveCharacter(character).getBody().getData();
        var resourceData = getResource.retrieveResource(missingResourceCode).getBody().getData();
        return characterData.getMiningLevel() >= resourceData.getLevel();
    }

    //todo this can go away once logic lives in ValidResourceStock
    private boolean validToGatherHerbResources(MyCharacters character, String missingResourceCode) {
        var characterData = getCharacter.retrieveCharacter(character).getBody().getData();
        var resourceData = getResource.retrieveResource(missingResourceCode).getBody().getData();
        return characterData.getAlchemyLevel() >= resourceData.getLevel();
    }

    private void moveToDestination(MyCharacters character, DestinationSchema destination) {
        var response = actionMove.move(character, destination);
        if (response.getStatusCode().is2xxSuccessful()) {
            sleep.sleep(character, response.getBody().getData().getCooldown().getRemainingSeconds());;
        }
    }

    private void transitionToAnotherLayer(MyCharacters character) {
        var response = actionTransition.transition(character);
        if (response.getStatusCode().is2xxSuccessful()) {
            sleep.sleep(character, response.getBody().getData().getCooldown().getRemainingSeconds());;
        }
    }

    private void depositItemsToBank(MyCharacters character, List<SimpleItemSchema> itemsDepositPayload) {
        var response = actionDepositBankItem.deposit(character, itemsDepositPayload);
        if (response.getStatusCode().is2xxSuccessful()) {
            sleep.sleep(character, response.getBody().getData().getCooldown().getRemainingSeconds());;
        }
    }

    private ResponseEntity<SkillResponseSchema> gather(MyCharacters character) {
        var response = actionGathering.gather(character);
        if (response.getStatusCode().is2xxSuccessful()) {
            sleep.sleep(character, response.getBody().getData().getCooldown().getRemainingSeconds());
        }
        return response;
    }

    private boolean inventoryIsFull(ResponseEntity<SkillResponseSchema> response) {
        return response.getStatusCode().value() == CHARACTER_INVENTORY_FULL;
    }
}
