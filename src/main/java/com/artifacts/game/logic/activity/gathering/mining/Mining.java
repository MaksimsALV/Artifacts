package com.artifacts.game.logic.activity.gathering.mining;

import com.artifacts.game.config.Characters;
import com.artifacts.game.endpoints.characters.GetCharacter;
import com.artifacts.game.endpoints.characters.GetCharacterWIP;
import com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem;
import com.artifacts.game.endpoints.mycharacters.ActionGathering;
import com.artifacts.game.endpoints.mycharacters.ActionMove;

import java.util.List;
import java.util.Map;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_CHARACTER_INVENTORY_FULL;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.characters.GetCharacter.CHARACTER;
import static com.artifacts.game.endpoints.characters.GetCharacterWIP.*;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.tools.Delay.delay;

public class Mining {
    public static final Map<String, int[]> ORE = Map.of(
            "COPPER", new int[]{2, 0}
            //add more
    );

    public static void miningCopper() throws InterruptedException {
        getCharacter(Characters.getGatherer());
        var characterPositionX = Integer.parseInt(CHARACTER_GATHERER.get(0).get("x").toString());
        var characterPositionY = Integer.parseInt(CHARACTER_GATHERER.get(0).get("y").toString());

        int[] miningCoordinates = ORE.get("COPPER");
        var x = miningCoordinates[0];
        var y = miningCoordinates[1];

        if (characterPositionX != x || characterPositionY != y) {
            actionMove(x, y);
        }

        while (true) {
            var gatheringResult = ActionGathering.actionGathering();
            if (gatheringResult != null && gatheringResult.statusCode() == CODE_SUCCESS) {
                continue;
            }
            if (gatheringResult != null && gatheringResult.statusCode() == CODE_CHARACTER_INVENTORY_FULL) {
                var moveToBank = actionMove(4, 1);
                if (moveToBank != null && moveToBank.statusCode() != CODE_SUCCESS) break;

                var depositAllItems = actionDepositBankItem();
                if (depositAllItems != null && depositAllItems.statusCode() != CODE_SUCCESS) break;

                var moveToMine = actionMove(x, y);
                if (moveToMine != null && moveToMine.statusCode() != CODE_SUCCESS) break;

                continue;
            }
            break;
        }
    }
}