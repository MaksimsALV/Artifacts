package com.artifacts.game.logic.activity.fighting;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionFight.actionFight;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
import static com.artifacts.game.library.monsters.Monsters.MONSTERS;

public class Fighting {
    public static void fight(String name, String monster) throws InterruptedException {
        int[] monsterCoordinates = MONSTERS.get(monster.toUpperCase());
        var x = monsterCoordinates[0];
        var y = monsterCoordinates[1];

        int cooldown = 0;
        String reason = "";
        var hp = 0;
        var statusCode = 0;

        var actionMoveResponseObject = actionMove(name, x, y);
        statusCode = actionMoveResponseObject.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            cooldown = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
            reason = actionMoveResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
            if (cooldown > 0) {
                System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                Thread.sleep(cooldown * 1000L);
            }
        }

        var actionRestResponseObject = actionRest(name);
        statusCode = actionRestResponseObject.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            cooldown = actionRestResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
            reason = actionRestResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
            if (cooldown > 0) {
                System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                Thread.sleep(cooldown * 1000L);
            }
        }

        while (true) {
            var actionFightResponseObject = actionFight(name);
            statusCode = actionFightResponseObject.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                cooldown = actionFightResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                reason = actionFightResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                hp = actionFightResponseObject.getJSONObject("data").getJSONObject("character").getInt("hp");
                if (cooldown > 0) {
                    System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                    Thread.sleep(cooldown * 1000L);
                } else if (hp <= 150) {
                    actionRestResponseObject = actionRest(name);
                    if (actionRestResponseObject != null) {
                        cooldown = actionRestResponseObject.getJSONObject("data").getJSONObject("cooldown").getInt("remaining_seconds");
                        reason = actionRestResponseObject.getJSONObject("data").getJSONObject("cooldown").getString("reason");
                        if (cooldown > 0) {
                            System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
                            Thread.sleep(cooldown * 1000L);
                        }
                    }
                }
                continue;

            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                var actionMoveToBankResponseObject = actionMove(name, 4, 1); //move to bank
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
                        fight(name, monster);
                        return;
                    }
                }
                fight(name, monster);
                return;

            } else if (statusCode == CODE_MAP_CONTENT_NOT_FOUND) {
                fight(name, monster);
                return;
            }
            return;
        }
    }
}

