package com.artifacts.game.service;

import com.artifacts.api.caching.CacheResources;
import com.artifacts.api.service.mycharacters.ActionGathering;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Sleep;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.SkillResponseSchema;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatheringService {
    private final ActionGathering actionGathering;
    private final CharacterService characterService;
    private final CacheResources cacheResources;
    private final Sleep sleep;

    public ResponseEntity<SkillResponseSchema> gather(MyCharacters character) {
        var response = actionGathering.gather(character);
        if (actionGathering.success(response)) {
            sleep.sleep(character, response.getBody().getData().getCooldown().getRemainingSeconds());
        }
        return response;
    }

    public boolean fullInventory(ResponseEntity<SkillResponseSchema> response) {
        return actionGathering.errorCharacterInventoryFull(response);
    }

    public boolean allowedToGather(MyCharacters character, String missingResourceCode) {
        var characterData = characterService.characterGatheringSkills(character);
        var resourceData = cacheResources.getCachedResources().stream()
                .filter(resource -> resource.getCode().equals(missingResourceCode))
                .findFirst()
                .orElseThrow();
        return characterData.get(resourceData.getSkill()) >= resourceData.getLevel();
    }
}
