/*
package com.artifacts.game.logic.activity.fighting;

import com.artifacts.game.endpoints.maps.GetAllMaps;
import com.artifacts.tools.GlobalHealthManager2;
import com.artifacts.tools.GlobalHealthManager3;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionFight.actionFight;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
import static com.artifacts.service.ConsumableManager.checkInventoryConsumables;
import static com.artifacts.service.ConsumableManager.getConsumables;
import static com.artifacts.service.UtilityEquipmentManager.*;
import static com.artifacts.service.GlobalCooldownManager.globalCooldownManager;

public class Fighting2 {
    public static void
    fight(String name, String activityLocation, String utilityOne, String utilityTwo, String consumable) throws InterruptedException {
        //todo spamming getCharacter here a lot. Need to call it once here, then not call it in below mentioned methods at all. Else spamming like crazy
        equipUtilitySlotOne(name, utilityOne);
        equipUtilitySlotTwo(name, utilityTwo);
        getConsumables(name, consumable);

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
            if (checkUtilitySlotOne(name, utilityOne) || checkUtilitySlotTwo(name, utilityTwo) || !checkInventoryConsumables(name, consumable)) {
                fight(name, activityLocation, utilityOne, utilityTwo, consumable);
                break;
            }

            response = actionFight(name);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
                //GlobalHealthManager2.globalHealthManager(name, response);
                GlobalHealthManager3.globalHealthManager(name, response, consumable); //todo testing improved GHM3
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
                    fight(name, activityLocation, utilityOne, utilityTwo, consumable);
                    return;

                }
                fight(name, activityLocation, utilityOne, utilityTwo, consumable);
                return;

            } else if (statusCode == CODE_MAP_CONTENT_NOT_FOUND) {
                fight(name, activityLocation, utilityOne, utilityTwo, consumable);
                return;
            }
            return;
        }
    }
}

 */

