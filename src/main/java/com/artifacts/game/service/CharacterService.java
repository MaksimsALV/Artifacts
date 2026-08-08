package com.artifacts.game.service;

import com.artifacts.api.service.character.GetCharacter;
import com.artifacts.game.account.MyCharacters;
import lombok.RequiredArgsConstructor;
import org.openapitools.client.model.SimpleItemSchema;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final GetCharacter getCharacter;

    public List<SimpleItemSchema> characterInventoryItems(MyCharacters character) {
        var response = getCharacter.retrieveCharacter(character);

        return response.getBody().getData().getInventory().stream()
                .filter(nonEmptyItem -> nonEmptyItem.getQuantity() > 0)
                .map(item -> {
                    SimpleItemSchema inventoryItem = new SimpleItemSchema();
                    inventoryItem.setCode(item.getCode());
                    inventoryItem.setQuantity(item.getQuantity());
                    return inventoryItem;
                })
                .toList();
    }
}
