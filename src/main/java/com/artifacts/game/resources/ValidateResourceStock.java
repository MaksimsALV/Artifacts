package com.artifacts.game.resources;

import com.artifacts.api.service.account.GetBankItems;
import com.artifacts.api.service.resources.GetAllResources;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.GatheringSkill;
import org.openapitools.client.model.ResourceSchema;
import org.openapitools.client.model.SimpleItemSchema;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValidateResourceStock {
    private final GetBankItems getBankItems;
    private final GetAllResources getAllResources;

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
                //todo this is a temporary hardcoded ignore list for now, to avoid going into the underground/other zones till code supports that
                .filter(code -> !ignoredResourceCodes().contains(code))
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

    public List<String> ignoredResourceCodes() {
        return List.of(
                "strange_rocks",
                "adamantite_rocks",
                "magic_tree",
                "palm_tree",
                "swordfish_spot",
                "lava_fish_spot",
                "glowstem",
                "enchanted_mushroom",
                "torch_cactus"
                );
    }
}
