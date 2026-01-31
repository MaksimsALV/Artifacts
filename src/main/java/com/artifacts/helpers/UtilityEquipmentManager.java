package com.artifacts.helpers;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_CHARACTER_INVENTORY_FULL;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.api.endpoints.get.GetCharacter.getCharacter;
import static com.artifacts.api.endpoints.post.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.api.endpoints.post.ActionEquipItem.actionEquipItem;
import static com.artifacts.api.endpoints.post.ActionMove.actionMove;
import static com.artifacts.api.endpoints.post.ActionWithdrawBankItem.actionWithdrawBankItem;
import static com.artifacts.helpers.GlobalCooldownManager.globalCooldownManager;

public class UtilityEquipmentManager {
    public static boolean checkUtilitySlotOne(String name, String utilityOne) {
        var utilityEquipmentSlotOne = getCharacter(name).getJSONObject("data").getString("utility1_slot");
        var utilitySlotOneIsEmpty = utilityEquipmentSlotOne.isEmpty() && !utilityOne.isEmpty();
        return utilitySlotOneIsEmpty;
    }
    public static boolean checkUtilitySlotTwo(String name, String utilityTwo) {
        var utilityEquipmentSlotTwo = getCharacter(name).getJSONObject("data").getString("utility2_slot");
        var utilitySlotTwoIsEmpty = utilityEquipmentSlotTwo.isEmpty() && !utilityTwo.isEmpty();
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
                response = actionEquipItem(name, utilityOne, "utility1", 100);
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }
            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                response = actionDepositBankItem(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    equipUtilitySlotOne(name, utilityOne);
                }
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
                response = actionEquipItem(name, utilityTwo, "utility2", 100);
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }
            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                response = actionDepositBankItem(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    equipUtilitySlotTwo(name, utilityTwo);
                }
            }
        }
    }
}
