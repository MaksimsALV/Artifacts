package com.artifacts.game.resources;

import com.artifacts.api.service.account.GetBankItems;
import com.artifacts.api.service.maps.GetAllMaps;
import com.artifacts.api.service.mycharacters.ActionGathering;
import com.artifacts.api.service.mycharacters.ActionMove;
import com.artifacts.api.service.resources.GetAllResources;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Sleep;
import org.openapitools.client.model.DestinationSchema;
import org.openapitools.client.model.GatheringSkill;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.artifacts.api.HttpCodes.SUCCESS;

@Service
public class MiningResources {
    private final GetBankItems getBankItems;
    private final GetAllResources getAllResources;
    private final GetAllMaps getAllMaps;
    private final ActionMove actionMove;
    private final ActionGathering actionGathering;
    private final Sleep sleep;

    public MiningResources(
            GetBankItems getBankItems,
            GetAllResources getAllResources,
            GetAllMaps getAllMaps,
            ActionMove actionMove,
            ActionGathering actionGathering,
            Sleep sleep) {
        this.getBankItems = getBankItems;
        this.getAllResources = getAllResources;
        this.getAllMaps = getAllMaps;
        this.actionMove = actionMove;
        this.actionGathering = actionGathering;
        this.sleep = sleep;
    }

    public void gatherMiningResources() {
        var resources = getAllResources.retrieveAllResources(GatheringSkill.MINING);
        var bankItems = getBankItems.retrieveBankItems();

        var bankItemsWithValues = bankItems.getBody().getData().stream()
                .collect(Collectors.toMap(
                        item -> item.getCode(),
                        item -> item.getQuantity()
                ));

        resources.getBody().getData().stream()
                .filter(resource -> bankItemsWithValues.getOrDefault(resource.getCode(), 0) < 1000)
                .findFirst()
                .ifPresent(resource -> {
                    var resourceCode = resource.getCode();

                    System.out.println("Not enough " + resourceCode + " in bank");

                    var maps = getAllMaps.retrieveAllMaps(resourceCode);
                    if (maps.getBody().getData() == null || maps.getBody().getData().isEmpty()) {
                        return;
                    }
                    var location = maps.getBody().getData().getFirst();
                    var destination = new DestinationSchema()
                            .x(location.getX())
                            .y(location.getY())
                            .mapId(location.getMapId());
                    actionMove.move(MyCharacters.MINER, destination);

                    while (true) {
                        var gather = actionGathering.gather(MyCharacters.MINER.getName());
                        if (gather.getStatusCode().value() == SUCCESS) {
                            sleep.sleep(gather.getBody().getData().getCooldown().getRemainingSeconds());
                        }
                    }
                });
    }
}
