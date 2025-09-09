package com.artifacts.game.logic.activity.crafting;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.items.GetItem.getItem;
import static com.artifacts.game.endpoints.maps.GetAllMaps.getAllMaps;
import static com.artifacts.game.endpoints.mycharacters.ActionCrafting.actionCrafting;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem.*;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

public class CraftingGear {
    public static void craftGear(String name, String activityLocation, String item, int quantity) throws InterruptedException {

        var response = getAllMaps(activityLocation);
        var dataArray = response.getJSONArray("data");
        var x = dataArray.getJSONObject(0).getInt("x");
        var y = dataArray.getJSONObject(0).getInt("y");

        response = getItem(item);
        var dataObject = response.getJSONObject("data");
        var craftingItem = dataObject.getString("code");
        var craftingIngredients = dataObject.getJSONObject("craft").getJSONArray("items");

        response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS || statusCode == CODE_CHARACTER_ALREADY_MAP) {
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
            while (true) {
                response = actionCrafting(name, craftingItem, quantity);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    continue;

                } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL || statusCode == CODE_MISSING_ITEM || statusCode == CODE_NOT_FOUND) {
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

                    //todo actually have to rewrite this hardcoded monstrosity into for loop to get ingredients from array and quantity
                    //todo also i dont think it will work if there is no 2 ingredients. so I have to always change that in code
                    var ingredientCodeOne = craftingIngredients.getJSONObject(0).getString("code");
                    response = actionWithdrawBankItem(name, ingredientCodeOne, 100);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
//                    var ingredientCodeTwo = craftingIngredients.getJSONObject(1).getString("code");
//                    response = actionWithdrawBankItem(name, ingredientCodeTwo, 50);
//                    statusCode = response.getInt("statusCode");
//                    if (statusCode == CODE_SUCCESS) {
//                        globalCooldownManager(name, response);
//                    }
                    craftGear(name, activityLocation, item, quantity);
                    return;
                }
                return;
            }

        } else if (statusCode == CODE_MISSING_ITEM || statusCode == CODE_NOT_FOUND) {
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

            //todo actually have to rewrite this hardcoded monstrosity into for loop to get ingredients from array and quantity
            //todo also i dont think it will work if there is no 2 ingredients. so I have to always change that in code
            var ingredientCodeOne = craftingIngredients.getJSONObject(0).getString("code");
            response = actionWithdrawBankItem(name, ingredientCodeOne, 50);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
//            var ingredientCodeTwo = craftingIngredients.getJSONObject(1).getString("code");
//            response = actionWithdrawBankItem(name, ingredientCodeTwo, 100);
//            statusCode = response.getInt("statusCode");
//            if (statusCode == CODE_SUCCESS) {
//                globalCooldownManager(name, response);
//            }
        }
    }
}