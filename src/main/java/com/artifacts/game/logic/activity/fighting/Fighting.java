package com.artifacts.game.logic.activity.fighting;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionFight.actionFight;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
import static com.artifacts.game.library.monsters.Monsters.MONSTERS;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

public class Fighting {
    public static void fight(String name, String monster) throws InterruptedException {
        int[] monsterCoordinates = MONSTERS.get(monster.toUpperCase());
        var x = monsterCoordinates[0];
        var y = monsterCoordinates[1];

        var response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }

        response = actionRest(name);
        statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }

        while (true) {
            response = actionFight(name);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                var hp = response.getJSONObject("data").getJSONObject("character").getInt("hp");
                globalCooldownManager(name, response);
                if (hp <= 150) {
                response = actionRest(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
                }
                continue;

            } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                response = actionMove(name, 4, 1); //move to bank
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }

                response = actionDepositBankItem(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    fight(name, monster);
                    return;

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

