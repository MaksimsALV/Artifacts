package com.artifacts.game.logic.activity.crafting;

import com.artifacts.game.endpoints.mycharacters.ActionMove;

import static com.artifacts.game.endpoints.mycharacters.ActionCrafting.actionCrafting;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.library.workshops.Workshops.WORKSHOPS;
import static com.artifacts.game.library.gear.Gear.*;
import static com.artifacts.game.library.gear.Weapons.*;

public class Crafting {
    public static void craft(String name, String workshop, String item) throws InterruptedException {
        String helm = HELMS.get(item.toUpperCase());
        String armor = ARMORS.get(item.toUpperCase());
        String legs = LEGS.get(item.toUpperCase());
        String boots = BOOTS.get(item.toUpperCase());
        String rings = RINGS.get(item.toUpperCase());
        String shield = SHIELDS.get(item.toUpperCase());
        String dagger = DAGGERS.get(item.toUpperCase());
        String axe = AXES.get(item.toUpperCase());

        int[] workshopCoordinates = WORKSHOPS.get(workshop.toUpperCase());
        var x = workshopCoordinates[0];
        var y = workshopCoordinates[1];

        int cooldown = 0;
        String reason = "";

        var actionMoveResponseObject = ActionMove.actionMove(name, x, y);
        if (actionMoveResponseObject != null) {
            cooldown = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
            reason = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
            if (cooldown > 0) {
                System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
            }
        }

        while (true) {
            var actionCraftingResponseObject = actionCrafting(name, item, 1);
            //todo right now im moving only when status is non-200. But later i need to change that, to move ONLY when inventory is full.
            //todo so for this i need to return also the status it self within actionGathering method, i need to do that at some point
            if (actionCraftingResponseObject != null) {
                cooldown = actionCraftingResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionCraftingResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
                }
                continue;
            }

            var actionMoveToBankResponseObject = ActionMove.actionMove(name, 4, 1); //move to bank
            if (actionMoveToBankResponseObject != null) {
                cooldown = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionMoveToBankResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
                }

                var actionDepositAllItemsResponseObject = actionDepositBankItem(name);
                if (actionDepositAllItemsResponseObject != null) {
                    cooldown = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                    reason = actionDepositAllItemsResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                    if (cooldown > 0) {
                        System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                        Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
                        craft(name, workshop, item);
                        return;
                    }
                }
                return;
            }
            return;
        }
    }
}