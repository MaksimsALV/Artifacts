package com.artifacts.game.logic.activity.crafting;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionCrafting.actionCrafting;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem.actionWithdrawBankItem;
import static com.artifacts.game.library.recources.Resources.CRAFTING_RESOURCES;
import static com.artifacts.game.library.recources.Resources.CRAFTING_RESOURCE_INGREDIENTS;
import static com.artifacts.game.library.workshops.Workshops.WORKSHOPS;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

public class CraftingResources {
    public static void craftResources(String name, String workshop, String item, String ingredient) throws InterruptedException {
        String craftingIngredient = CRAFTING_RESOURCE_INGREDIENTS.get(ingredient);
        String craftingItem = CRAFTING_RESOURCES.get(item);

        int[] workshopCoordinates = WORKSHOPS.get(workshop.toUpperCase());
        var x = workshopCoordinates[0];
        var y = workshopCoordinates[1];

        var response = actionMove(name, x, y);
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

            response = actionWithdrawBankItem(name, craftingIngredient, 100);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
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
            response = actionCrafting(name, craftingItem, 10);
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

                response = actionWithdrawBankItem(name, craftingIngredient, 100);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }
                craftResources(name, workshop, item, ingredient);
                return;
            }
            return;
        }
    }
}
