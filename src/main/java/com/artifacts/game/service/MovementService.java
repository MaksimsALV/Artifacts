package com.artifacts.game.service;

import com.artifacts.api.caching.CacheMyCharacters;
import com.artifacts.api.service.mycharacters.ActionMove;
import com.artifacts.game.account.MyCharacters;
import com.artifacts.tools.Sleep;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.DestinationSchema;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovementService {
    private final ActionMove actionMove;
    private final CacheMyCharacters cacheMyCharacters;
    private final Sleep sleep;

    public void moveToDestination(MyCharacters character, DestinationSchema destination) {
        var response = actionMove.move(character, destination);
        if (actionMove.success(response)) {
            var cooldown = response.getBody().getData().getCooldown().getRemainingSeconds();
            var characterData = response.getBody().getData().getCharacter();

            cacheMyCharacters.updateCharacter(characterData);
            sleep.sleep(character, cooldown);
        }
    }
}
