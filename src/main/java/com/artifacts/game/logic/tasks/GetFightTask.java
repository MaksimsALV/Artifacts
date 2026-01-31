package com.artifacts.game.logic.tasks;

import com.artifacts.api.endpoints.get.GetAllMaps;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.endpoints.get.GetCharacter.getCharacter;
import static com.artifacts.api.endpoints.post.ActionAcceptNewTask.actionAcceptNewTask;
import static com.artifacts.api.endpoints.post.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.api.endpoints.post.ActionMove.actionMove;
import static com.artifacts.api.endpoints.post.ActionTaskCancel.actionTaskCancel;
import static com.artifacts.api.endpoints.post.ActionWithdrawBankItem.actionWithdrawBankItem;
import static com.artifacts.game.logic.tasks.FightTaskLimitator.fightTaskIsValidForExecution;
import static com.artifacts.helpers.GlobalCooldownManager.globalCooldownManager;

//todo maybe merge in FightingTask tbh as it is my great design template - or maybe not.
public class GetFightTask {
    public static String getFightTask(String name) throws InterruptedException {
        var coordinates = GetAllMaps.getAllMaps("monsters");
        var x = coordinates.getJSONArray("data").getJSONObject(0).getInt("x");
        var y = coordinates.getJSONArray("data").getJSONObject(0).getInt("y");

        var response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }
        response = actionAcceptNewTask(name);
        statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            var taskCode = response.getJSONObject("data").getJSONObject("task").getString("code");
            globalCooldownManager(name, response);
            if (fightTaskIsValidForExecution(taskCode)) { //todo need to move this whole block to somewhere else, too heavy to read what it does
                return taskCode;
            } else {
                response = actionTaskCancel(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    return getFightTask(name);
                } else if (statusCode == CODE_MISSING_ITEM) {
                    response = actionMove(name, 4, 1);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
                    response = actionWithdrawBankItem(name, "tasks_coin", 1);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                        return getFightTask(name);
                    } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                        response = actionDepositBankItem(name);
                        statusCode = response.getInt("statusCode");
                        if (statusCode == CODE_SUCCESS) {
                            globalCooldownManager(name, response);
                            return getFightTask(name);
                        }
                    }
                }
            }
        } else if (statusCode == CODE_CHARACTER_ALREADY_TASK) {
            var taskCode = getCharacter(name).getJSONObject("data").getString("task");
            if (fightTaskIsValidForExecution(taskCode)) { //todo need to move this whole block to somewhere else, too heavy to read what it does
                return taskCode;
            } else {
                response = actionTaskCancel(name);
                statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    return getFightTask(name);
                } else if (statusCode == CODE_MISSING_ITEM) {
                    response = actionMove(name, 4, 1);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
                    response = actionWithdrawBankItem(name, "tasks_coin", 1);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                        return getFightTask(name);
                    } else if (statusCode == CODE_CHARACTER_INVENTORY_FULL) {
                        response = actionDepositBankItem(name);
                        statusCode = response.getInt("statusCode");
                        if (statusCode == CODE_SUCCESS) {
                            globalCooldownManager(name, response);
                            return getFightTask(name);
                        }
                    }
                }
            }
            return taskCode;
        }
        return null;
        //return taskCode;
    }
}
