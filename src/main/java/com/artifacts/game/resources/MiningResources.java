package com.artifacts.game.resources;

import com.artifacts.api.service.account.GetBankItems;
import com.artifacts.api.service.resources.GetAllResources;
import org.openapitools.client.model.GatheringSkill;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MiningResources {
    private final GetBankItems getBankItems;
    private final GetAllResources getAllResources;

    public MiningResources(GetBankItems getBankItems, GetAllResources getAllResources) {
        this.getBankItems = getBankItems;
        this.getAllResources = getAllResources;
    }

    public void gatherMiningResources() {
        var resources = getAllResources.retrieveAllResources(GatheringSkill.MINING);
        var bankItems = getBankItems.retrieveBankItems();

        var resourceCodes = resources.getBody().getData().stream()
                .map(code -> code.getCode())
                .toList();

        var bankItemsWithValues = bankItems.getBody().getData().stream()
                .collect(Collectors.toMap(
                        item -> item.getCode(),
                        item -> item.getQuantity()
                ));

        for (var resourceCode : resourceCodes) {
            var resourceAmount = bankItemsWithValues.getOrDefault(resourceCode, 0);

            if (resourceAmount < 1000) {
                System.out.println("Not enough " + resourceCode + " in bank");
                //execute action to gather
            }
        }
    }

//    public boolean validateStock() {
//
//        return null;
//    }
}
