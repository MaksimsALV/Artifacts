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

    private static final int RESOURCE_THRESHOLD = 1000;
    private static final List<String> IGNORED_RESOURCE_CODES = List.of(
            "gold_rocks",
            "mithril_rocks",
            "strange_rocks",
            "adamantite_rocks",
            "magic_tree",
            "palm_tree",
            "swordfish_spot",
            "lava_fish_spot",
            "nettle",
            "glowstem",
            "enchanted_mushroom",
            "torch_cactus"
    );

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

    public List<String> retrieveResourceCodesAsList(GatheringSkill skill) {
        return cacheResources.getCachedResources().stream()
                .filter(resource -> resource.getSkill() == skill)
                .map(ResourceSchema::getCode)
                .filter(code -> code != null)
                .filter(code -> !IGNORED_RESOURCE_CODES.contains(code))
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
}
