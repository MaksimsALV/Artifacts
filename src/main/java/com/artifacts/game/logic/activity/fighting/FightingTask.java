package com.artifacts.game.logic.activity.fighting;

import com.artifacts.game.endpoints.maps.GetAllMaps;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionAcceptNewTask.actionAcceptNewTask;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionFight.actionFight;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
import static com.artifacts.game.logic.activity.tasks.CompleteFightTask.completeFightTask;
import static com.artifacts.game.logic.activity.tasks.GetFightTask.getFightTask;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;
import static com.artifacts.tools.GlobalHealthManager.unhealthy;

public class FightingTask {
    public static void fightTask(String name, String activityLocation) throws InterruptedException {
        //this then starts getFightTask and in the end which returns activityLocation string, which is then starts whole loop with getting activity coordinates and starting loop
        activityLocation = getFightTask(name);
        var coordinates = GetAllMaps.getAllMaps(activityLocation);
        var x = coordinates.getJSONArray("data").getJSONObject(0).getInt("x");
        var y = coordinates.getJSONArray("data").getJSONObject(0).getInt("y");

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
            var taskTotal = response.getJSONObject("data").getJSONObject("character").getInt("task_total");
            var taskCurrent = response.getJSONObject("data").getJSONObject("character").getInt("task_progress");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
                if (taskCurrent == taskTotal) {
                    completeFightTask(name); //todo check if this works as i want. When all done i execute completeFightTask, then start fightTask again from scratch, stopping current loop
                    fightTask(name, activityLocation);
                    return;
                }
                if (unhealthy(response)) {
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
                    fightTask(name, activityLocation);
                    return;

                }
                fightTask(name, activityLocation);
                return;

            } else if (statusCode == CODE_MAP_CONTENT_NOT_FOUND) {
                fightTask(name, activityLocation);
                return;
            }
            return;
        }
    }
}
