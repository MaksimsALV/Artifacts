package com.artifacts.game.engine.strategy.level1;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;

import static com.artifacts.game.endpoints.characters.GetCharacter.positionX;
import static com.artifacts.game.endpoints.characters.GetCharacter.positionY;

public class Level1 {
    public static void runLevel1Strategy() {
        GetCharacter.getCharacter();
        if (GetCharacter.positionX != 0 && GetCharacter.positionY != 1) {
            ActionMove.actionMove(0, 1);
        } else {
            System.out.println("runLevel1Stragegy: already on (0,1), not moving");
        }
    }
}
