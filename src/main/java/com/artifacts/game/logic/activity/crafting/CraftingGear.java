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
            while (true) {
                var actionCraftingResponseObject = actionCrafting(name, item, 1);
                statusCode = actionCraftingResponseObject.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    cooldown = actionCraftingResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                    reason = actionCraftingResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                    if (cooldown > 0) {
                        System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                        Thread.sleep(cooldown * 1000L);
                    }
                    continue;

                } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL || statusCode == CODE_MISSING_ITEM || statusCode == CODE_NOT_FOUND) {
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

                    var actionWithdrawItemsResponseObject = actionWithdrawBankItem(name, craftingResourceIngredient, 100);
                    statusCode = actionWithdrawItemsResponseObject.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        cooldown = actionWithdrawItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                        reason = actionWithdrawItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                        if (cooldown > 0) {
                            System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                            Thread.sleep(cooldown * 1000L);
                        }
                    }
                    craftGear(name, workshop, item);
                    return;
                }
                return;
            }

        } else if (statusCode == CODE_MISSING_ITEM || statusCode == CODE_NOT_FOUND) {
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

            var actionWithdrawItemsResponseObject = actionWithdrawBankItem(name, craftingResourceIngredient, 100);
            statusCode = actionWithdrawItemsResponseObject.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                cooldown = actionWithdrawItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionWithdrawItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L);
                }
            }
        }
    }
}