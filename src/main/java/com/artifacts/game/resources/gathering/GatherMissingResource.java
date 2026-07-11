package com.artifacts.game.resources.gathering;

import com.artifacts.api.service.character.GetCharacter;
import com.artifacts.api.service.maps.GetAllMaps;
import com.artifacts.api.service.mycharacters.ActionDepositBankItem;
import com.artifacts.api.service.mycharacters.ActionGathering;
import com.artifacts.api.service.mycharacters.ActionMove;
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
    private final Sleep sleep;
    private final ValidateResourceStock validateResourceStock;

    public GatherMissingResource(
            GetAllMaps getAllMaps,
            GetCharacter getCharacter,
            ActionMove actionMove,
            ActionGathering actionGathering,
            ActionDepositBankItem actionDepositBankItem,
            Sleep sleep,
            ValidateResourceStock validateResourceStock) {
        this.getAllMaps = getAllMaps;
        this.getCharacter = getCharacter;
        this.actionMove = actionMove;
        this.actionGathering = actionGathering;
        this.actionDepositBankItem = actionDepositBankItem;
        this.sleep = sleep;
        this.validateResourceStock = validateResourceStock;
    }

    @Async
    public void gatherMissingResource(MyCharacters character, GatheringSkill skill) {
        while (true) {
            var missingResourceCode = validateResourceStock.missingResourceCode(skill);

            if (missingResourceCode == null) {
                return;
            }

            var missingResourceDestination = retrieveDestinationForMissingResource(missingResourceCode);
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

    private DestinationSchema retrieveDestinationForMissingResource(String missingResourceCode) {
        var maps = getAllMaps.retrieveAllMaps(null, missingResourceCode);
        var locationData = maps.getBody().getData().getFirst();
        return new DestinationSchema()
                .x(locationData.getX())
                .y(locationData.getY())
                .mapId(locationData.getMapId());
    }

    private DestinationSchema retrieveDestinationForForestMainBank() {
        var maps = getAllMaps.retrieveAllMaps(MapContentType.BANK, null);
        var locationData = maps.getBody().getData().stream()
                .filter(forestMainBank -> forestMainBank.getMapId() == 334)
                .findFirst()
                .orElseThrow();

        return new DestinationSchema()
                .x(locationData.getX())
                .y(locationData.getY())
                .mapId(locationData.getMapId());
    }

    private void moveToDestination(MyCharacters character, DestinationSchema destination) {
        var response = actionMove.move(character, destination);
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
