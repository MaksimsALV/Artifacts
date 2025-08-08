package com.artifacts.game.logic.leveling;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionFight;

import java.util.HashMap;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.characters.GetCharacter.*;
import static com.artifacts.game.endpoints.mycharacters.ActionFight.*;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.*;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.*;
import static com.artifacts.tools.Delay.delay;

public class L5 {
public static void runLoop() throws InterruptedException {
    System.out.println("\nStarting L5 initial check");
    delay(5);
        while (!statusOk()) {
            System.out.println("\nStatus is not okay, fixing....");
            getCharacter();
            if (badPosition()) {
                actionMove(0,1);
            } else if (badHealth()) {
                actionRest();
            }
        }

    System.out.println("\nAll issues are fixed. Starting infinite loop");
    delay(10);
        while (true) {
            actionFight();
            HashMap<String, String> characterData = ActionFight.FIGHT.get(0);
            var hp = Integer.parseInt(characterData.get("hp"));
            if (hp <= 60) {
                actionRest();
            }

            //todo if inventory is full > move to bank > deposit > return to loop
            //if (actionFight().statusCode() == CODE_CHARACTER_INVENTORY_FULL) {

            //}
        }
    }

    public static boolean statusOk() {
        int x = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("x"));
        int y = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("y"));
        int hp = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("hp"));
        return (x == 0 && y == 1) && (hp >= 61);
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




