package com.artifacts.game.logic.activity;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.endpoints.get.GetItem.getItem;
import static com.artifacts.api.endpoints.get.GetAllMaps.getAllMaps;
import static com.artifacts.api.endpoints.post.ActionCrafting.actionCrafting;
import static com.artifacts.api.endpoints.post.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.api.endpoints.post.ActionMove.actionMove;
import static com.artifacts.api.endpoints.post.ActionWithdrawBankItem.actionWithdrawBankItem;
import static com.artifacts.game.helpers.GlobalCooldownManager.globalCooldownManager;

public class Crafting {
    public static void craft(String name, String activityLocation, String item, int quantity) throws InterruptedException {
        if (item.equals("copper_bar") || item.equals("iron_bar") || item.equals("steel_bar") || item.equals("sapphire") || item.equals("emerald") || item.equals("ruby") || item.equals("gold_bar") || item.equals("obsidian_bar")) {
            activityLocation = "mining";
        } //todo need to have this for all crafting types, need some helper

        var response = getAllMaps(activityLocation);
        var dataArray = response.getJSONArray("data");
        var x = dataArray.getJSONObject(0).getInt("x");
        var y = dataArray.getJSONObject(0).getInt("y");

        response = getItem(item);
        var dataObject = response.getJSONObject("data");
        var craftingItem = dataObject.getString("code");
        var craftingIngredientsList = dataObject.getJSONObject("craft").getJSONArray("items");

        response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS || statusCode == CODE_CHARACTER_ALREADY_MAP) {
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
        }

        if (statusCode == CODE_MISSING_ITEM) {
            response = actionMove(name, 4, 1);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }

            response = actionDepositBankItem(name);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }

            for (var i = 0; i < craftingIngredientsList.length(); i++) {
                var craftingIngredient = craftingIngredientsList.getJSONObject(i);
                var craftingIngredientCode = craftingIngredient.getString("code");
                var craftingIngredientQuantity = craftingIngredient.getInt("quantity") * quantity;
                response = actionWithdrawBankItem(name, craftingIngredientCode, craftingIngredientQuantity);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }
            }

            response = actionMove(name, x, y);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS || statusCode == CODE_CHARACTER_ALREADY_MAP) {
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }
            }
        }

        while (true) {
            response = actionCrafting(name, craftingItem, quantity);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
                continue;

            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL || statusCode == CODE_MISSING_ITEM) {
                response = actionMove(name, 4, 1);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }

                response = actionDepositBankItem(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }

                for (var i = 0; i < craftingIngredientsList.length(); i++) {
                    var craftingIngredient = craftingIngredientsList.getJSONObject(i);
                    var craftingIngredientCode = craftingIngredient.getString("code");
                    var craftingIngredientQuantity = craftingIngredient.getInt("quantity") * quantity;
                    response = actionWithdrawBankItem(name, craftingIngredientCode, craftingIngredientQuantity);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
                }
                craft(name, activityLocation, item, quantity);
                return;
            }
            return;
        }
    }
}
