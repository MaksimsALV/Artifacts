package com.artifacts.service;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem.actionWithdrawBankItem;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

public class UtilityEquipmentManager {
    public static boolean checkUtilitySlotOne(String name, String utilityOne) {
        var utilityEquipmentSlotOneStatus = getCharacter(name).getJSONObject("data").getString("utility1_slot");
        var utilitySlotOneIsEmpty = utilityEquipmentSlotOneStatus.isEmpty() && !utilityOne.isEmpty();
        return utilitySlotOneIsEmpty;
    }
    public static boolean checkUtilitySlotTwo(String name, String utilityTwo) {
        var utilityEquipmentSlotTwoStatus = getCharacter(name).getJSONObject("data").getString("utility2_slot");
        var utilitySlotTwoIsEmpty = utilityEquipmentSlotTwoStatus.isEmpty() && !utilityTwo.isEmpty();
        return utilitySlotTwoIsEmpty;
    }

    public static void equipUtilitySlotOne(String name, String utilityOne) throws InterruptedException {
        if (checkUtilitySlotOne(name, utilityOne)) {
            var response = actionMove(name, 4, 1);
            var statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
            response = actionWithdrawBankItem(name, utilityOne, 100);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
                //todo equip
            }
        }
    }

    public static void equipUtilitySlotTwo(String name, String utilityTwo) throws InterruptedException {
        if (checkUtilitySlotTwo(name, utilityTwo)) {
            var response = actionMove(name, 4, 1);
            var statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
            response = actionWithdrawBankItem(name, utilityTwo, 100);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
                //todo equip
            }
        }
    }
}
