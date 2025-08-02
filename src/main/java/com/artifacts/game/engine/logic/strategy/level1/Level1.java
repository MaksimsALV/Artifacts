package com.artifacts.game.engine.logic.strategy.level1;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionFight;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import com.artifacts.game.endpoints.mycharacters.ActionRest;
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
                System.out.println("runLevel1Stragegy: Position: already on (0,1), not moving");
            }

            if (GetCharacter.hp <= 60) {
                System.out.println("runLevel1Stragegy: HP below 60, resting");
                ActionRest.actionRest();
            } else {
                System.out.println("runLevel1Stragegy: HP above 61, fighting");
                ActionFight.actionFight();
            }


            try {
                Thread.sleep(10000);
            } catch (InterruptedException interruptedExceptionError) {
                System.out.println("runLevel1StragegyInfiniteLoop: interrupted: " + interruptedExceptionError);
                break;
            }
        }
    }
}
