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
public class ValidateResourceStock {
    private final GetBankItems getBankItems;
    private final GetAllResources getAllResources;

    public ValidateResourceStock(GetBankItems getBankItems, GetAllResources getAllResources) {
        this.getBankItems = getBankItems;
        this.getAllResources = getAllResources;
    }

    public boolean resourceStockHasMissingItems(GatheringSkill skill) {
        return missingResourceCode(skill) != null;
    }

    public String missingResourceCode(GatheringSkill skill) {
        var resources = retrieveResourceCodesAsList(skill);
        var bankItems = retrieveAllResourcesFromBankAsMap();

        return resources.stream().filter(resourceCode -> bankItems.getOrDefault(resourceCode, 0) < 1000)
                .findFirst()
                .orElse(null);
    }

    public List<String> retrieveResourceCodesAsList(GatheringSkill skill) {
        return getAllResources.retrieveAllResources(skill).getBody().getData().stream()
                .map(ResourceSchema::getCode)
                .toList();
    }

    public Map<String, Integer> retrieveAllResourcesFromBankAsMap() {
        var allBankItems = getBankItems.retrieveBankItems();

        return allBankItems.getBody().getData().stream()
                .collect(Collectors.toMap(
                        SimpleItemSchema::getCode,
                        SimpleItemSchema::getQuantity
                ));
    }
}
