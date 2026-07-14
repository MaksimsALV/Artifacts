package com.artifacts.game.resources.gathering;

import com.artifacts.api.service.character.GetCharacter;
import com.artifacts.api.service.maps.GetAllMaps;
import com.artifacts.api.service.mycharacters.ActionDepositBankItem;
import com.artifacts.api.service.mycharacters.ActionGathering;
import com.artifacts.api.service.mycharacters.ActionMove;
import com.artifacts.api.service.mycharacters.ActionTransition;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.game.resources.ValidateResourceStock;
import com.artifacts.tools.Sleep;
import org.openapitools.client.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.artifacts.api.HttpCodes.CHARACTER_INVENTORY_FULL;

@Service
public class GatherMissingResource {
    private final GetAllMaps getAllMaps;
    private final GetCharacter getCharacter;
    private final ActionMove actionMove;
    private final ActionGathering actionGathering;
    private final ActionDepositBankItem actionDepositBankItem;
    private final ActionTransition actionTransition;
    private final Sleep sleep;
    private final ValidateResourceStock validateResourceStock;
    private final LocationService locationService;

    public GatherMissingResource(
            GetAllMaps getAllMaps,
            GetCharacter getCharacter,
            ActionMove actionMove,
            ActionGathering actionGathering,
            ActionDepositBankItem actionDepositBankItem,
            ActionTransition actionTransition,
            Sleep sleep,
            ValidateResourceStock validateResourceStock,
            LocationService locationService) {
        this.getAllMaps = getAllMaps;
        this.getCharacter = getCharacter;
        this.actionMove = actionMove;
        this.actionGathering = actionGathering;
        this.actionDepositBankItem = actionDepositBankItem;
        this.actionTransition = actionTransition;
        this.sleep = sleep;
        this.validateResourceStock = validateResourceStock;
        this.locationService = locationService;
    }
//todo this needs rewrite into smaller subclass helpers, else it gets too long
//todo use LocationService -> migrate all location related methods there.
//todo also I think I need something like " successful activity service " to handle all: moveto & sleep, transitoon & sleep etc. - but need to think
    @Async
    public void gatherMissingResource(MyCharacters character, GatheringSkill skill) {
        while (true) {
            var missingResourceCode = validateResourceStock.missingResourceCode(skill);

            if (missingResourceCode == null) {
                return;
            }
            var mapData = retrieveMapDataForMissingResourceCode(missingResourceCode);
            var missingResourceDestination = retrieveDestinationForMissingResource(mapData);
//            var missingResourceDestination = resourceDestinationService.resourceLocation(missingResourceCode);

            if ("gold_rocks".equals(missingResourceCode)) {
                moveThroughMineEntrance(character, retrieveGoldMineEntrance());
//                moveToDestination(character, resourceDestinationService.goldMineLocation());

            } else if ("mithril_rocks".equals(missingResourceCode)) {
                moveThroughMineEntrance(character, retrieveMithrilMineEntrance());
//                moveToDestination(character, resourceDestinationService.mithrilMineLocation());

            }

            moveToDestination(character, missingResourceDestination);

            while (true) {
                var gather = gather(character);

                if (inventoryIsFull(gather)) {
                    var bankDestination = retrieveDestinationForForestMainBank();
                    moveToDestination(character, bankDestination);
                    var itemsDepositPayload = retrieveItemsFromCharacterInventoryAsList(character);
                    depositItemsToBank(character, itemsDepositPayload);
                    break;
                }
            }
        }
    }

    private void moveThroughMineEntrance(MyCharacters character, DestinationSchema mineEntrance) {
        moveToDestination(character, mineEntrance);
        transitionToAnotherLayer(character);
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

    private ResponseEntity<StaticDataPageMapSchema> retrieveMapDataForMissingResourceCode(String missingResourceCode) {
        return getAllMaps.retrieveAllMaps(null, null, missingResourceCode, null);
    }

    private DestinationSchema retrieveDestinationForMissingResource(ResponseEntity<StaticDataPageMapSchema> maps) {
        var locationData = maps.getBody().getData().getFirst();
        return new DestinationSchema()
                .x(locationData.getX())
                .y(locationData.getY())
                .mapId(locationData.getMapId());
    }

    private DestinationSchema retrieveDestinationForForestMainBank() {
        var maps = getAllMaps.retrieveAllMaps(null, MapContentType.BANK, null, null);
        var locationData = maps.getBody().getData().stream()
                .filter(forestMainBank -> forestMainBank.getMapId() == 334)
                .findFirst()
                .orElseThrow();

        return new DestinationSchema()
                .x(locationData.getX())
                .y(locationData.getY())
                .mapId(locationData.getMapId());
    }

    private DestinationSchema retrieveGoldMineEntrance() {
        return new DestinationSchema()
                .x(5)
                .y(-3)
                .mapId(134);
    }

    private DestinationSchema retrieveMithrilMineEntrance() {
        return new DestinationSchema()
                .x(-2)
                .y(6)
                .mapId(571);
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
