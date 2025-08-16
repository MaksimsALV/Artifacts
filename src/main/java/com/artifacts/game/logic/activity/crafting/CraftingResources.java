package com.artifacts.game.logic.activity.crafting;

import com.artifacts.game.endpoints.mycharacters.ActionMove;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionCrafting.actionCrafting;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionWithdrawBankItem.actionWithdrawBankItem;
import static com.artifacts.game.library.gear.Gear.*;
import static com.artifacts.game.library.gear.Weapons.*;
import static com.artifacts.game.library.recources.Resources.CRAFTING_RESOURCES;
import static com.artifacts.game.library.recources.Resources.CRAFTING_RESOURCE_INGREDIENTS;
import static com.artifacts.game.library.workshops.Workshops.WORKSHOPS;

public class CraftingResources {
    public static void craftResources(String name, String workshop, String item, String ingredient) throws InterruptedException {
        //resources
        String craftingIngredient = CRAFTING_RESOURCE_INGREDIENTS.get(ingredient);
        String craftingItem = CRAFTING_RESOURCES.get(item);

        int[] workshopCoordinates = WORKSHOPS.get(workshop.toUpperCase());
        var x = workshopCoordinates[0];
        var y = workshopCoordinates[1];

        var cooldown = 0;
        var reason = "";
        var statusCode = 0;

        var actionMoveResponseObject = ActionMove.actionMove(name, x, y);
        statusCode = actionMoveResponseObject.getInt("statusCode");
        if (statusCode == CODE_SUCCESS || statusCode == CODE_CHARACTER_ALREADY_MAP) {
            if (statusCode == CODE_SUCCESS) {
                cooldown = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L);
                }
            }
        }

        if (statusCode == CODE_MISSING_ITEM) {
            var actionMoveToBankResponseObject = ActionMove.actionMove(name, 4, 1); //move to bank
            statusCode = actionMoveToBankResponseObject.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                cooldown = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L);
                }
            }

            var actionDepositAllItemsResponseObject = actionDepositBankItem(name);
            statusCode = actionDepositAllItemsResponseObject.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                cooldown = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L);
                }
            }

            var actionWithdrawItemsResponseObject = actionWithdrawBankItem(name, craftingIngredient, 100);
            statusCode = actionWithdrawItemsResponseObject.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                cooldown = actionWithdrawItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionWithdrawItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L);
                }
            }
            actionMoveResponseObject = ActionMove.actionMove(name, x, y);
            statusCode = actionMoveResponseObject.getInt("statusCode");
            if (statusCode == CODE_SUCCESS || statusCode == CODE_CHARACTER_ALREADY_MAP) {
                if (statusCode == CODE_SUCCESS) {
                    cooldown = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                    reason = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                    if (cooldown > 0) {
                        System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                        Thread.sleep(cooldown * 1000L);
                    }
                }
            }
        }

        while (true) {
            var actionCraftingResponseObject = actionCrafting(name, craftingItem, 10);
            statusCode = actionCraftingResponseObject.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                cooldown = actionCraftingResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionCraftingResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L);
                }
                continue;

            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL || statusCode == CODE_MISSING_ITEM) {
                var actionMoveToBankResponseObject = ActionMove.actionMove(name, 4, 1); //move to bank
                statusCode = actionMoveToBankResponseObject.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    cooldown = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                    reason = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                    if (cooldown > 0) {
                        System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                        Thread.sleep(cooldown * 1000L);
                    }
                }

                var actionDepositAllItemsResponseObject = actionDepositBankItem(name);
                statusCode = actionDepositAllItemsResponseObject.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    cooldown = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                    reason = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                    if (cooldown > 0) {
                        System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                        Thread.sleep(cooldown * 1000L);
                    }
                }

                var actionWithdrawItemsResponseObject = actionWithdrawBankItem(name, craftingIngredient, 100);
                statusCode = actionWithdrawItemsResponseObject.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    cooldown = actionWithdrawItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                    reason = actionWithdrawItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                    if (cooldown > 0) {
                        System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                        Thread.sleep(cooldown * 1000L);
                    }
                }
                craftResources(name, workshop, item, ingredient);
                return;
            }
            return;
        }
    }
}
