package com.artifacts.game.resources;

import com.artifacts.api.caching.CacheBankItems;
import com.artifacts.api.caching.CacheResources;
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
    private final CacheResources cacheResources;
    private final CacheBankItems cacheBankItems;
    private final int RESOURCE_THRESHOLD = 1000;

    public boolean resourceStockHasMissingItems(GatheringSkill skill) {
        return missingResourceCode(skill) != null;
    }

    public String missingResourceCode(GatheringSkill skill) {
        var resources = retrieveResourceCodesAsList(skill);
        var bankItems = retrieveAllResourcesFromBankAsMap();

        return resources.stream().filter(resourceCode -> bankItems.getOrDefault(resourceCode, 0) < RESOURCE_THRESHOLD)
                .findFirst()
                .orElse(null);
    }

    //todo need to add character validity checker here, and if character is unavailable to farm the resource, it should ignore it, then all GatherMissingResrouce logic on booleans can go away.
    public List<String> retrieveResourceCodesAsList(GatheringSkill skill) {
        return cacheResources.getCachedResources().stream()
                .filter(resource -> resource.getSkill() == skill)
                .map(ResourceSchema::getCode)
                .filter(code -> code != null)
                //todo this is a temporary hardcoded ignore list for now, to avoid going into the underground/other zones till code supports that
                .filter(code -> !ignoredResourceCodes().contains(code))
                .toList();
    }


    public Map<String, Integer> retrieveAllResourcesFromBankAsMap() {
        var allBankItems = cacheBankItems.getCachedBankItems();

        return allBankItems.stream()
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
