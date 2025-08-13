package com.artifacts.game.logic.activity.fighting.L1_10;

import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItemWIP.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionFightWIP.actionFight;
import static com.artifacts.game.endpoints.mycharacters.ActionMoveWIP.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionRestWIP.actionRest;
import static com.artifacts.game.library.monsters.Monsters.MONSTERS;

public class ChickenWIP {
    public static void fightChicken(String name) throws InterruptedException {
        int[] chickenCoordinates = MONSTERS.get("CHICKEN");
        var x = chickenCoordinates[0];
        var y = chickenCoordinates[1];

        int cooldown = 0;
        String reason = "";
        var hp = 0;

        var actionMoveResponseObject = actionMove(name, x, y);
        if (actionMoveResponseObject != null) {
            cooldown = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
            reason = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
            if (cooldown > 0) {
                System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
            }
        }

        var actionRestResponseObject = actionRest(name);
        if (actionRestResponseObject != null) {
            cooldown = actionRestResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
            reason = actionRestResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
            if (cooldown > 0) {
                System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
            }
        }

        while (true) {
            var actionFightResponseObject = actionFight(name);
            if (actionFightResponseObject != null) {
                cooldown = actionFightResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionFightResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                hp = actionFightResponseObject.getJSONObject("data").getJSONObject("character").getInt("hp");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
                } else if (hp <= 60) {
                    actionRestResponseObject = actionRest(name);
                    if (actionRestResponseObject != null) {
                        cooldown = actionRestResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                        reason = actionRestResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                        if (cooldown > 0) {
                            System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                            Thread.sleep(cooldown * 1000L); //todo do i need to define thread or it understand it self?
                        }
                    }
                }
                continue;
            }

            var actionMoveToBankResponseObject = actionMove(name, 4, 1); //move to bank
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
                        fightChicken(name);
                        return;
                    }
                }
                return;
            }
            return;
        }
    }
}

