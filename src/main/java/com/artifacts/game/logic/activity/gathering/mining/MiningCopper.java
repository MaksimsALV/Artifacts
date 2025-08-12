/*
package com.artifacts.game.logic.activity.gathering.mining;

import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem;
import com.artifacts.game.endpoints.mycharacters.ActionGathering;
import com.artifacts.game.endpoints.mycharacters.ActionMove;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.tools.Delay.delay;

public class MiningCopper {
    public static void miningCopperLoop() throws InterruptedException {
        while (!statusOk()) {
            System.out.println("\nStatus is not okay, fixing....");
            getCharacter();
            if (badPosition()) {
                ActionMove.actionMove(2,0);
            }
        }

        System.out.println("\nAll issues are fixed. Starting infinite loop");
        delay(10);
        while (true) {
            var gatheringResponse = ActionGathering.actionGathering();
            if (gatheringResponse.statusCode() == CODE_CHARACTER_INVENTORY_FULL) {
                actionMove(4,1);
                var depositResponse = ActionDepositBankItem.actionDepositBankItem();
                if (depositResponse.statusCode() == CODE_SUCCESS) { //if NPE happens, add fightResponse.statusCode() != null &&...
                    miningCopperLoop();
                    return; //quitting the current loop to start status checking again
                } else {
                    System.err.println("Fatal error occurred. Exiting..."); //total system killer if I cannot dump inventory and repeat on the loop
                    System.exit(999);
                }
            }
        }

    }

    public static boolean statusOk() {
        getCharacter();
        var x = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("x"));
        var y = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("y"));
        return x == 2 && y == 0;
    }

    public static boolean badPosition() {
        getCharacter();
        var x = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("x"));
        var y = Integer.parseInt(GetCharacter.CHARACTER.get(0).get("y"));
        return x != 2 || y != 0;
    }
}

 */
