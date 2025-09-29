package com.artifacts.game.logic.activity.gathering;

import com.artifacts.game.endpoints.maps.GetAllMaps;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionGathering.actionGathering;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
//import static com.artifacts.game.library.locations.GatheringZones.RESOURCE_FIELDS;
//import static com.artifacts.game.library.recources.Resources.RESOURCE_LOCATION;
import static com.artifacts.service.GlobalCooldownManager.globalCooldownManager;

public class Gathering {
    public static void gather(String name, String activityLocation) throws InterruptedException {
        //if (Thread.currentThread().isInterrupted()) throw new InterruptedException("cancelled");
        var coordinates = GetAllMaps.getAllMaps(activityLocation);
        var x = coordinates.getJSONArray("data").getJSONObject(0).getInt("x");
        var y = coordinates.getJSONArray("data").getJSONObject(0).getInt("y");

        var response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }

        while (true) {
            response = actionGathering(name);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
                continue;
            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                if (activityLocation.equals("nettle") || activityLocation.equals("glowstem") || activityLocation.equals("trout_fishing_spot") || activityLocation.equals("bass_fishing_spot")) {
                    response = actionMove(name, 7, 13);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    } else {
                        response = actionMove(name, 4, 1);
                        statusCode = response.getInt("statusCode");
                        if (statusCode == CODE_SUCCESS) {
                            globalCooldownManager(name, response);
                        }
                    }
                } else {
                    response = actionMove(name, 4, 1);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
                }
                response = actionDepositBankItem(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }
                gather(name, activityLocation);
                return;
            }
            return;
        }
    }
}