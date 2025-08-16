package com.artifacts.game.logic.activity.gathering;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_CHARACTER_INVENTORY_FULL;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionGathering.actionGathering;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.library.recources.Resources.RESOURCES;

public class Gathering {
    public static void gather(String name, String resource) throws InterruptedException {
        int[] resourceCoordinates = RESOURCES.get(resource.toUpperCase());
        var x = resourceCoordinates[0];
        var y = resourceCoordinates[1];

        var cooldown = 0;
        var reason = "";
        var statusCode = 0;

        var actionMoveResponseObject = actionMove(name, x, y);
        statusCode = actionMoveResponseObject.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            cooldown = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
            reason = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
            if (cooldown > 0) {
                System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                Thread.sleep(cooldown * 1000L);
            }
        }

        while (true) {
            var actionGatheringResponseObject = actionGathering(name);
            statusCode = actionGatheringResponseObject.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                cooldown = actionGatheringResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionGatheringResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L);
                }
                continue;
            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                var actionMoveToBankResponseObject = actionMove(name, 4, 1); //move to bank
                statusCode = actionMoveToBankResponseObject.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    cooldown = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                    reason = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                    if (cooldown > 0) {
                        System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                        Thread.sleep(cooldown * 1000L);
                    }
                }

                var actionDepositAllItemsResponseObject = actionDepositBankItem(name);
                statusCode = actionDepositAllItemsResponseObject.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    cooldown = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                    reason = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                    if (cooldown > 0) {
                        System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                        Thread.sleep(cooldown * 1000L);
                        gather(name, resource);
                        return;
                    }
                }
                gather(name, resource);
                return;
            }
            return;
        }
    }
}