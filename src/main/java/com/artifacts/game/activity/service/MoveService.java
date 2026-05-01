package com.artifacts.game.activity.service;

import com.artifacts.api.service.mycharacters.ActionMove;
import com.artifacts.game.Events;
import com.artifacts.game.account.MyCharacters;
import org.openapitools.client.model.CharacterMovementDataSchema;
import org.openapitools.client.model.CharacterMovementResponseSchema;
import org.openapitools.client.model.DestinationSchema;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

@Service
public class MoveService {
    private final ActionMove actionMove;

    public MoveService(ActionMove actionMove) {
        this.actionMove = actionMove;
    }

    @EventListener(Events.AllCharactersAreReadyEvent.class)

    //todo temporary for tests purposes only.
    public ResponseEntity<CharacterMovementResponseSchema> moveToChicken() {
        DestinationSchema destination = new DestinationSchema();
        destination.setX(0);
        destination.setY(1);
//        destination.mapId(322);
        try {
            var response = actionMove.move(MyCharacters.WARRIOR, destination);
            return response;
        } catch (RestClientResponseException e) {
            return ResponseEntity.status(e.getStatusCode()).build(); //todo temporary
        }
    }
}
