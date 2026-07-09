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
            if (actionMove.moveSuccess(move)) {
                sleep.sleep(actionMove.cooldownSecondsAfterMoveSuccess(move));
            }

            while (true) {
                var gather = actionGathering.gather(MyCharacters.MINER.getName());
                if (actionGathering.gatherSuccess(gather)) {
                    sleep.sleep(actionGathering.cooldownSecondsAfterGatherSuccess(gather));
                }
            }
        }
    }

    private DestinationSchema retrieveDestinationForMissingResource(String missingResourceCode) {
        var maps = getAllMaps.retrieveAllMaps(missingResourceCode);
        var locationData = maps.getBody().getData().getFirst();
        return new DestinationSchema()
                .x(locationData.getX())
                .y(locationData.getY())
                .mapId(locationData.getMapId());
    }
}
