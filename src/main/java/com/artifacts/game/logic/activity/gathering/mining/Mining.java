package com.artifacts.game.logic.activity.gathering.mining;

import com.artifacts.game.config.Characters;
import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.characters.GetCharacterWIP;
import com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem;
import com.artifacts.game.endpoints.mycharacters.ActionGathering;
import com.artifacts.game.endpoints.mycharacters.ActionMove;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_CHARACTER_INVENTORY_FULL;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.characters.GetCharacter.CHARACTER;
//import static com.artifacts.game.endpoints.characters.GetCharacterWIP.*;
//import static com.artifacts.game.endpoints.characters.GetCharacterWIP.getCharacter;
import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.tools.Delay.delay;
import static com.artifacts.tools.Scheduler.scheduler;

public class Mining {
    public static final Map<String, int[]> ORE = Map.of(
            "COPPER", new int[]{2, 0}
            //add more
    );

    public static void miningCopper() throws InterruptedException {

        //testing getting object directly from response and not using lists.
        var responseDataObject = GetCharacter.getCharacterResponseDataObject(); //todo need to change to getCharacterWIP with getGatherer in the parameter, but it is still wrong
        var characterPositionX = responseDataObject.getInt("x");
        var characterPositionY = responseDataObject.getInt("y");
        //var characterPositionX = Integer.parseInt(CHARACTER.get(0).get("x").toString()); //todo i think i need to get this info from getCharacter(getGatherer) response itself
        //var characterPositionY = Integer.parseInt(CHARACTER.get(0).get("y").toString()); //todo i think i need to get this info from getCharacter(getGatherer) response itself

        int[] miningCoordinates = ORE.get("COPPER");
        var x = miningCoordinates[0];
        var y = miningCoordinates[1];

        if (characterPositionX != x || characterPositionY != y) {
            var moveToMine = actionMove(x, y);

            if (moveToMine != null) { //todo this seems to work, but need to add it to below loop too then
                var cooldown = Integer.parseInt(ActionMove.MOVE.get(1).get("remaining_seconds")); //todo i think i need to get this info from actionMove response itself
                scheduler.schedule(() -> {
                    try {
                        miningCopper();
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
                var moveToBank = actionMove(4, 1);
                if (moveToBank != null && moveToBank.statusCode() != CODE_SUCCESS) break;

                var depositAllItems = actionDepositBankItem();
                if (depositAllItems != null && depositAllItems.statusCode() != CODE_SUCCESS) break;

                var moveToMine = actionMove(x, y);
                if (moveToMine != null && moveToMine.statusCode() != CODE_SUCCESS) break;

                continue; //replace with return
            }
            break;
        }
    }
}