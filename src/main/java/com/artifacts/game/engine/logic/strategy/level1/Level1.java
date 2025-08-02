package com.artifacts.game.engine.logic.strategy.level1;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import com.artifacts.game.endpoints.mycharacters.GetMyCharacters;

import static com.artifacts.game.endpoints.characters.GetCharacter.positionX;
import static com.artifacts.game.endpoints.characters.GetCharacter.positionY;

public class Level1 {
    public static void runLevel1Strategy() throws InterruptedException {
        while (true) {
            GetCharacter.getCharacter();
            if (GetCharacter.positionX != 0 && GetCharacter.positionY != 1) {
                ActionMove.actionMove(0, 1);
            } else {
                System.out.println("runLevel1Stragegy: already on (0,1), not moving");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedExceptionError) {
                System.out.println("runLevel1StragegyInfiniteLoop: interrupted: " + interruptedExceptionError);
                break;
            }
        }
    }
}
