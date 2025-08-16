package com.artifacts.game.logic.activity.crafting;

import com.artifacts.game.endpoints.mycharacters.ActionMove;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionCrafting.actionCrafting;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem.*;
import static com.artifacts.game.library.recources.Resources.CRAFTING_RESOURCES;
import static com.artifacts.game.library.recources.Resources.CRAFTING_RESOURCE_INGREDIENTS;
import static com.artifacts.game.library.workshops.Workshops.WORKSHOPS;
import static com.artifacts.game.library.gear.Gear.*;
import static com.artifacts.game.library.gear.Weapons.*;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

public class CraftingGear {
    public static void craftGear(String name, String workshop, String item) throws InterruptedException {
        //resources
        String craftingResource = CRAFTING_RESOURCES.get(item.toUpperCase());
        String craftingResourceIngredient = CRAFTING_RESOURCE_INGREDIENTS.get(item.toUpperCase());

        //gear
        String helm = HELMS.get(item.toUpperCase());
        String armor = ARMORS.get(item.toUpperCase());
        String legs = LEGS.get(item.toUpperCase());
        String boots = BOOTS.get(item.toUpperCase());
        String rings = RINGS.get(item.toUpperCase());

        //weapons
        String shield = SHIELDS.get(item.toUpperCase());
        String dagger = DAGGERS.get(item.toUpperCase());
        String axe = AXES.get(item.toUpperCase());

        int[] workshopCoordinates = WORKSHOPS.get(workshop.toUpperCase());
        var x = workshopCoordinates[0];
        var y = workshopCoordinates[1];

        var response = ActionMove.actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS || statusCode == CODE_CHARACTER_ALREADY_MAP) {
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
            while (true) {
                response = actionCrafting(name, item, 1);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    continue;

                } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL || statusCode == CODE_MISSING_ITEM || statusCode == CODE_NOT_FOUND) {
                    response = ActionMove.actionMove(name, 4, 1);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }

                    response = actionDepositBankItem(name);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }

                    response = actionWithdrawBankItem(name, craftingResourceIngredient, 20);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
                    craftGear(name, workshop, item);
                    return;
                }
                return;
            }

        } else if (statusCode == CODE_MISSING_ITEM || statusCode == CODE_NOT_FOUND) {
            response = ActionMove.actionMove(name, 4, 1);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }

            response = actionDepositBankItem(name);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }

            response = actionWithdrawBankItem(name, craftingResourceIngredient, 20);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
        }
    }
}