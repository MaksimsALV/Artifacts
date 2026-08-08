package com.artifacts.game.service;

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
    private final Sleep sleep;

    public void moveToDestination(MyCharacters character, DestinationSchema destination) {
        var response = actionMove.move(character, destination);
        var cooldown = response.getBody().getData().getCooldown().getRemainingSeconds();

        sleep.sleep(character, cooldown);
    }
}
