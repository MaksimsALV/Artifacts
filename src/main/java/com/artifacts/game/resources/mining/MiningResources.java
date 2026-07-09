package com.artifacts.game.resources.mining;

import com.artifacts.api.service.account.GetBankItems;
import com.artifacts.api.service.maps.GetAllMaps;
import com.artifacts.api.service.mycharacters.ActionGathering;
import com.artifacts.api.service.mycharacters.ActionMove;
import com.artifacts.api.service.resources.GetAllResources;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.game.resources.Validate;
import com.artifacts.tools.Sleep;
import org.openapitools.client.model.DestinationSchema;
import org.openapitools.client.model.MapContentType;
import org.springframework.stereotype.Service;

@Service
public class MiningResources {
    private final GetBankItems getBankItems;
    private final GetAllResources getAllResources;
    private final GetAllMaps getAllMaps;
    private final ActionMove actionMove;
    private final ActionGathering actionGathering;
    private final Sleep sleep;
    private final Validate validate;

    public MiningResources(
            GetBankItems getBankItems,
            GetAllResources getAllResources,
            GetAllMaps getAllMaps,
            ActionMove actionMove,
            ActionGathering actionGathering,
            Sleep sleep,
            Validate validate) {
        this.getBankItems = getBankItems;
        this.getAllResources = getAllResources;
        this.getAllMaps = getAllMaps;
        this.actionMove = actionMove;
        this.actionGathering = actionGathering;
        this.sleep = sleep;
        this.validate = validate;
    }

    public void gatherMiningResource() {
        var missingResourceCode = validate.validateResourceStock();
        if (missingResourceCode != null) {
            var missingResourceDestination = retrieveDestinationForMissingResource(missingResourceCode);

            var move = actionMove.move(MyCharacters.MINER, missingResourceDestination);
            if (actionMove.success(move)) {
                sleep.sleep(actionMove.cooldown(move));
            }

            while (true) {
                var gather = actionGathering.gather(MyCharacters.MINER.getName());
                if (actionGathering.success(gather)) {
                    sleep.sleep(actionGathering.cooldown(gather));
                }
                if (actionGathering.inventoryFull(gather)) {
                    var forestMainBank = retrieveDestinationForForestMainBank();
                    move = actionMove.move(MyCharacters.MINER, forestMainBank);
                    if (actionMove.success(move)) {
                        sleep.sleep(actionMove.cooldown(move));
                        //retrieve character inventory
                            //deposit
                                //start again
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
}
