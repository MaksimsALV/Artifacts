package com.artifacts.game.logic.activity.gathering.mining;

import com.artifacts.game.config.Characters;
//import com.artifacts.game.endpoints.characters.GetCharacter;
//import com.artifacts.game.endpoints.characters.GetCharacterWIP;
import com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem;
import com.artifacts.game.endpoints.mycharacters.ActionGathering;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
//import static com.artifacts.game.endpoints.characters.GetCharacter.CHARACTER;
//import static com.artifacts.game.endpoints.characters.GetCharacterWIP.*;
//import static com.artifacts.game.endpoints.characters.GetCharacterWIP.getCharacter;
//import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.config.Characters.getWarrior;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
//import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionMoveWIP.actionMove;
import static com.artifacts.tools.Delay.delay;
import static com.artifacts.tools.Scheduler.scheduler;

public class Mining {
    public static final Map<String, int[]> ORE = Map.of(
            "COPPER", new int[]{2, 0}
            //add more
    );

    public static void miningCopper(String name) throws InterruptedException {
        int[] miningCoordinates = ORE.get("COPPER");
        var x = miningCoordinates[0];
        var y = miningCoordinates[1];
        var responseDataObject = actionMove(name, x, y);

        if (responseDataObject != null) {
            var cooldown = responseDataObject.getJSONObject("cooldown").getInt("remaining_seconds");
            var reason = responseDataObject.getJSONObject("cooldown").getString("reason");
            if (cooldown > 0) {
                System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                scheduler.schedule(() -> {
                    try {
                        miningCopper(name);
                    } catch (InterruptedException schedulerException) {
                        Thread.currentThread().interrupt();
                    }
                }, cooldown, TimeUnit.SECONDS);
                return;
            }
        }

        while (true) {
            var gatheringResult = ActionGathering.actionGathering();
            if (gatheringResult != null && gatheringResult.statusCode() == CODE_SUCCESS) {
                continue; //replace with return
            }
            if (gatheringResult != null && gatheringResult.statusCode() == CODE_CHARACTER_INVENTORY_FULL) {
                var moveToBank = actionMove(name,4, 1);
                if (moveToBank != null) break;

                var depositAllItems = actionDepositBankItem();
                if (depositAllItems != null) break;

                miningCopper(name);
                return;
            }
            break;
        }
    }
}