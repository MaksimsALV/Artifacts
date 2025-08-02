package com.artifacts.game.engine.logic.strategy.level;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionFight;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import com.artifacts.game.endpoints.mycharacters.ActionRest;

public class Level1 {
    public static void runLevel1() throws InterruptedException {
        while (true) {
            GetCharacter.getCharacter();
            if (statusOK()) {
                System.out.println("runLevel1: statusOK, fighting!");
                ActionFight.actionFight();
            } else if (badPosition()) {
                System.out.println("runLevel1: badPosition, moving");
                ActionMove.actionMove(0, 1);
            } else if (badHealth()) {
                System.out.println("runLevel1: badHealth, resting");
                ActionRest.actionRest();
            }
        }
    }

    public static boolean statusOK() {
        return GetCharacter.positionX == 0 && GetCharacter.positionY == 1 && GetCharacter.hp >= 61;
    }

    public static boolean badPosition() {
        return GetCharacter.positionX != 0 && GetCharacter.positionY != 1;
    }

    public static boolean badHealth() {
        return GetCharacter.hp <= 60;
    }

}




