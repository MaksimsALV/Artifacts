package com.artifacts.game.resources;

import com.artifacts.api.service.account.GetBankItems;
import com.artifacts.api.service.resources.GetAllResources;
import org.openapitools.client.model.GatheringSkill;
import org.openapitools.client.model.ResourceSchema;
import org.openapitools.client.model.SimpleItemSchema;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Validate {
    private final GetBankItems getBankItems;
    private final GetAllResources getAllResources;

    public Validate(GetBankItems getBankItems, GetAllResources getAllResources) {
        this.getBankItems = getBankItems;
        this.getAllResources = getAllResources;
    }

    public String validateResourceStock() {
        var allMiningResources = retrieveAllMiningResourceCodesAsList();
        var allBankItems = retrieveAllResourcesFromBankAsMap();

        return allMiningResources.stream().filter(resourceCode -> allBankItems.getOrDefault(resourceCode, 0) < 1000)
                .findFirst()
                .orElse(null);
    }

    public Map<String, Integer> retrieveAllResourcesFromBankAsMap() {
        var allBankItems = getBankItems.retrieveBankItems();

        return allBankItems.getBody().getData().stream()
                .collect(Collectors.toMap(
                        SimpleItemSchema::getCode,
                        SimpleItemSchema::getQuantity
                ));
    }

    public List<String> retrieveAllMiningResourceCodesAsList() {
        var allMiningResources = getAllResources.retrieveAllResources(GatheringSkill.MINING);
        return allMiningResources.getBody().getData().stream()
                .map(ResourceSchema::getCode)
                .toList();
    }
}
