package com.artifacts.game.logic.activity.gathering.mining;

//import com.artifacts.game.endpoints.characters.GetCharacter;
//import com.artifacts.game.endpoints.characters.GetCharacterWIP;
//import com.artifacts.game.endpoints.mycharacters.ActionGathering;

import java.util.Map;
import java.util.concurrent.TimeUnit;

//import static com.artifacts.game.endpoints.characters.GetCharacter.CHARACTER;
//import static com.artifacts.game.endpoints.characters.GetCharacterWIP.*;
//import static com.artifacts.game.endpoints.characters.GetCharacterWIP.getCharacter;
//import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
//import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionGatheringWIP.actionGathering;
import static com.artifacts.game.endpoints.mycharacters.ActionMoveWIP.actionMove;
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
        var actionMoveResponseObject = actionMove(name, x, y);

        if (actionMoveResponseObject != null) {
            var cooldown = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
            var reason = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
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
            var actionGatheringResponseObject = actionGathering(name);
            if (actionGatheringResponseObject != null) {
                //todo i might be needing global cooldown handler, to clean that thing up
                var cooldown = actionGatheringResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                var reason = actionGatheringResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    scheduler.schedule(() -> {
                        try {
                            miningCopper(name); //should i have actionGathering(name) here instead?
                        } catch (InterruptedException schedulerException) {
                            Thread.currentThread().interrupt();
                        }
                    }, cooldown, TimeUnit.SECONDS);
                    return;
                }
                continue;
            }

            var actionMoveToBankResponseObject = actionMove(name, 4, 1); //move to bank
            if (actionMoveToBankResponseObject != null) {
                var cooldown = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                var reason = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
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

                var depositAllItems = actionDepositBankItem();
                if (depositAllItems != null) return;

                miningCopper(name);
                return;

                //break;
            }
            return;
        }
    }
}