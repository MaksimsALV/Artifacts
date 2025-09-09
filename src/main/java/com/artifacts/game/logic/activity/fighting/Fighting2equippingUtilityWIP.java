package com.artifacts.game.logic.activity.fighting;

import com.artifacts.game.endpoints.maps.GetAllMaps;
import com.artifacts.tools.GlobalHealthManager2;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionFight.actionFight;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
import static com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem.actionWithdrawBankItem;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

public class Fighting2equippingUtilityWIP {
    public static void fight(String name, String activityLocation, String utilityOne, String utilityTwo) throws InterruptedException {

        //todo this defo needs to be moved into separate class for readability
        var utilityEquipmentSlotOneStatus = getCharacter(name).getJSONObject("data").getString("utility1_slot");
        var utilityEquipmentSlotTwoStatus = getCharacter(name).getJSONObject("data").getString("utility2_slot");
        var needSlotOne = utilityEquipmentSlotOneStatus.isEmpty() && !utilityOne.isEmpty();
        var needSlotTwo = utilityEquipmentSlotTwoStatus.isEmpty() && !utilityTwo.isEmpty();
        if ((needSlotOne) || (needSlotTwo)) {
            var response = actionMove(name, 4, 1);
            var statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
            if (needSlotOne) {
                response = actionWithdrawBankItem(name, utilityOne, 100);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    //todo equip
                }
            }
            if (needSlotTwo) {
                response = actionWithdrawBankItem(name, utilityTwo, 100);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    //todo equip
                }
            }
        }

        var coordinates = GetAllMaps.getAllMaps(activityLocation);
        var x = coordinates.getJSONArray("data").getJSONObject(0).getInt("x");
        var y = coordinates.getJSONArray("data").getJSONObject(0).getInt("y");

        var response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }

        //todo should add globalHealthManager here too
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
                GlobalHealthManager2.globalHealthManager(name, response);
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
                    fight(name, activityLocation, utilityOne, utilityTwo);
                    return;

                }
                fight(name, activityLocation, utilityOne, utilityTwo);
                return;

            } else if (statusCode == CODE_MAP_CONTENT_NOT_FOUND) {
                fight(name, activityLocation, utilityOne, utilityTwo);
                return;
            }
            return;
        }
    }
}

