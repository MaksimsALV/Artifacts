package com.artifacts.game.logic.activity.gathering;

import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionGathering.actionGathering;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.library.recources.Resources.RESOURCES;

public class Gathering {
    public static void gather(String name, String resource) throws InterruptedException {
        int[] resourceCoordinates = RESOURCES.get(resource.toUpperCase());
        var x = resourceCoordinates[0];
        var y = resourceCoordinates[1];

        int cooldown = 0;
        String reason = "";

        var actionMoveResponseObject = actionMove(name, x, y);
        if (actionMoveResponseObject != null) {
            cooldown = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
            reason = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
            if (cooldown > 0) {
                System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
                //return; //think i need to remove it
            }
        }

        while (true) {
            var actionGatheringResponseObject = actionGathering(name);
            //todo right now im moving only when status is non-200. But later i need to change that, to move ONLY when inventory is full.
            //todo so for this i need to return also the status it self within actionGathering method, i need to do that at some point
            if (actionGatheringResponseObject != null) {
                cooldown = actionGatheringResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionGatheringResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
                }
                continue;
            }

            var actionMoveToBankResponseObject = actionMove(name, 4, 1); //move to bank
            if (actionMoveToBankResponseObject != null) {
                cooldown = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
                }

                var actionDepositAllItemsResponseObject = actionDepositBankItem(name);
                if (actionDepositAllItemsResponseObject != null) {
                    cooldown = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                    reason = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                    if (cooldown > 0) {
                        System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                        Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
                        gather(name, resource);
                        return;
                    }
                }
                /*
                var depositAllItems = actionDepositBankItem();
                if (depositAllItems != null) {
                    miningCopper(name);
                    return;
                }

                 */

                return;
            }
            return;
        }
    }
}