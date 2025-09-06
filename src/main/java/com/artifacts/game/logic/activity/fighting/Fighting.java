package com.artifacts.game.logic.activity.fighting;

import com.artifacts.game.endpoints.maps.GetAllMaps;
import com.artifacts.tools.GlobalHealthManager2;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionFight.actionFight;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
//import static com.artifacts.game.library.locations.Monsters.MONSTERS;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;
import static com.artifacts.tools.GlobalHealthManager.unhealthy;

public class Fighting {
    public static void fight(String name, String activityLocation) throws InterruptedException {
        //if (Thread.currentThread().isInterrupted()) throw new InterruptedException("cancelled");
        var coordinates = GetAllMaps.getAllMaps(activityLocation);
        var x = coordinates.getJSONArray("data").getJSONObject(0).getInt("x");
        var y = coordinates.getJSONArray("data").getJSONObject(0).getInt("y");

        var response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }

        response = actionRest(name);
        statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }

        while (true) {
            response = actionFight(name);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
                GlobalHealthManager2.globalHealthManager(name, response); //todo testing globalHealthManager
//                if (unhealthy(response)) {
//                response = actionRest(name);
//                statusCode = response.getInt("statusCode");
//                if (statusCode == CODE_SUCCESS) {
//                        globalCooldownManager(name, response);
//                    }
//                }
                continue;

            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                response = actionMove(name, 4, 1); //move to bank
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }

                response = actionDepositBankItem(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    fight(name, activityLocation);
                    return;

                }
                fight(name, activityLocation);
                return;

            } else if (statusCode == CODE_MAP_CONTENT_NOT_FOUND) {
                fight(name, activityLocation);
                return;
            }
            return;
        }
    }
}

