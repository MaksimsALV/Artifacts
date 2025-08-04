package com.artifacts.game.logic.leveling;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionFight;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import com.artifacts.game.endpoints.mycharacters.ActionRest;

public class L5 {
    public static void runLoop() throws InterruptedException {
        while (true) {
            //todo need to redo whole loop logic. Instead of calling getCharacter all the time for position and data, I should get that information directly from previous 200 response body
            GetCharacter.getCharacter();
            if (statusOK()) {
                System.out.println("L5: statusOK, fighting!");
                ActionFight.actionFight();
            } else if (badPosition()) {
                System.out.println("L5: badPosition, moving");
                ActionMove.actionMove(0, 1);
            } else if (badHealth()) {
                System.out.println("L5: badHealth, resting");
                ActionRest.actionRest();
            }
        }
    }

    //todo also this part needs to be changed all 3 methods), because I am constantly calling character list, while I should rely on previous 200 response body for data
    public static boolean statusOK() {
        int x = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("x"));
        int y = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("y"));
        int hp = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("hp"));
        return x == 0 && y == 1 && hp >= 61;
    }

    public static boolean badPosition() {
        int x = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("x"));
        int y = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("y"));
        return x != 0 || y != 1;
    }

    public static boolean badHealth() {
        int hp = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("hp"));
        return hp <= 60;
    }
}




