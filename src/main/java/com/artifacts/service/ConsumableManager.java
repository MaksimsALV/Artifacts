package com.artifacts.service;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_CHARACTER_INVENTORY_FULL;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.characters.GetCharacter.getCharacter;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionEquipItem.actionEquipItem;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem.actionWithdrawBankItem;
import static com.artifacts.game.library.items.GetItemsByItemType.getAllConsumableItems;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

public class ConsumableManager {
    public static boolean checkInventoryConsumables(String name, String consumable) {
        var inventoryConsumable = getCharacter(name).getJSONObject("data").getJSONArray("inventory");
        var consumableItem = getAllConsumableItems();
        for (var i = 0; i < inventoryConsumable.length(); i++) {
            var item = inventoryConsumable.getJSONObject(i);
            var itemName = item.getString("code");
            if (consumableItem.contains(itemName)) {
                return true;
            }
        }
        return false;
    }

    public static void getConsumables(String name, String consumable) throws InterruptedException {
        if (!checkInventoryConsumables(name, consumable)) { //if I dont have any consumables in inventory
            var response = actionMove(name, 4, 1);
            var statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
            response = actionWithdrawBankItem(name, consumable, 100);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                response = actionDepositBankItem(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    getConsumables(name, consumable);
                }
            }
        }
    }
}
