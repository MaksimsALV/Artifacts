package com.artifacts.game.service;

import com.artifacts.api.caching.CacheMyCharacters;
import com.artifacts.game.account.MyCharacters;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.GatheringSkill;
import org.openapitools.client.model.SimpleItemSchema;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CacheMyCharacters cacheMyCharacters;

    public List<SimpleItemSchema> characterInventoryItems(MyCharacters character) {
        var characterData = cacheMyCharacters.getCachedCharacter(character);

        return characterData.getInventory().stream()
                .filter(nonEmptyItem -> nonEmptyItem.getQuantity() > 0)
                .map(item -> {
                    SimpleItemSchema inventoryItem = new SimpleItemSchema();
                    inventoryItem.setCode(item.getCode());
                    inventoryItem.setQuantity(item.getQuantity());
                    return inventoryItem;
                })
                .toList();
    }

    public Map<GatheringSkill, Integer> characterGatheringSkills(MyCharacters character) {
        var characterData = cacheMyCharacters.getCachedCharacter(character);
        return Map.of(
                GatheringSkill.MINING, characterData.getMiningLevel(),
                GatheringSkill.WOODCUTTING, characterData.getWoodcuttingLevel(),
                GatheringSkill.FISHING, characterData.getFishingLevel(),
                GatheringSkill.ALCHEMY, characterData.getAlchemyLevel()
        );
    }
}
