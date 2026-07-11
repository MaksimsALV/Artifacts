package com.artifacts.game.resources.gathering;

import com.artifacts.api.service.account.GetBankItems;
import com.artifacts.api.service.character.GetCharacter;
import com.artifacts.api.service.maps.GetAllMaps;
import com.artifacts.api.service.mycharacters.ActionDepositBankItem;
import com.artifacts.api.service.mycharacters.ActionGathering;
import com.artifacts.api.service.mycharacters.ActionMove;
import com.artifacts.api.service.resources.GetAllResources;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.game.resources.ValidateResourceStock;
import com.artifacts.tools.Sleep;
import org.openapitools.client.model.DestinationSchema;
import org.openapitools.client.model.GatheringSkill;
import org.openapitools.client.model.MapContentType;
import org.openapitools.client.model.SimpleItemSchema;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GatherMissingResource {
    private final GetBankItems getBankItems;
    private final GetAllResources getAllResources;
    private final GetAllMaps getAllMaps;
    private final GetCharacter getCharacter;
    private final ActionMove actionMove;
    private final ActionGathering actionGathering;
    private final ActionDepositBankItem actionDepositBankItem;
    private final Sleep sleep;
    private final ValidateResourceStock validateResourceStock;

    public GatherMissingResource(
            GetBankItems getBankItems,
            GetAllResources getAllResources,
            GetAllMaps getAllMaps,
            GetCharacter getCharacter,
            ActionMove actionMove,
            ActionGathering actionGathering,
            ActionDepositBankItem actionDepositBankItem,
            Sleep sleep,
            ValidateResourceStock validateResourceStock) {
        this.getBankItems = getBankItems;
        this.getAllResources = getAllResources;
        this.getAllMaps = getAllMaps;
        this.getCharacter = getCharacter;
        this.actionMove = actionMove;
        this.actionGathering = actionGathering;
        this.actionDepositBankItem = actionDepositBankItem;
        this.sleep = sleep;
        this.validateResourceStock = validateResourceStock;
    }

    public void gatherMissingResource(MyCharacters character, GatheringSkill skill) {
        while (true) {
            var missingResourceCode = validateResourceStock.missingResourceCode(skill);
            if (missingResourceCode != null) {
                var missingResourceDestination = retrieveDestinationForMissingResource(missingResourceCode);

                moveToDestination(character, missingResourceDestination);

                while (true) {
                    var gather = actionGathering.gather(character);
                    //todo need to do this as funcitonal function
                    if (actionGathering.success(gather)) {
                        sleep.sleep(actionGathering.cooldown(gather));
                    }
                    if (actionGathering.inventoryFull(gather)) {
                        var forestMainBank = retrieveDestinationForForestMainBank();
                        moveToDestination(character, forestMainBank); {
                            var characterData = getCharacter.retrieveCharacter(character);
                            List<SimpleItemSchema> itemsDepositPayload = characterData.getBody().getData().getInventory().stream()
                                    .filter(nonEmptyItem -> nonEmptyItem.getQuantity() > 0)
                                    .map(item -> {
                                        SimpleItemSchema payload = new SimpleItemSchema();
                                        payload.setCode(item.getCode());
                                        payload.setQuantity(item.getQuantity());
                                        return payload;
                                    })
                                    .toList();
                            var deposit = actionDepositBankItem.deposit(character, itemsDepositPayload);
                            if (actionDepositBankItem.success(deposit)) {
                                sleep.sleep(actionDepositBankItem.cooldown(deposit));
                                break;
                            }
                        }
                    }
                }
            }
        }
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
        var move = actionMove.move(character, destination);
        if (actionMove.success(move)) {
            sleep.sleep(actionMove.cooldown(move));
        }
    }
}
