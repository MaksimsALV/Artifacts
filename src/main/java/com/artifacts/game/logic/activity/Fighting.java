package com.artifacts.game.logic.activity;

import com.artifacts.api.endpoints.get.GetAllMaps;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.api.endpoints.post.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.api.endpoints.post.ActionFight.actionFight;
import static com.artifacts.api.endpoints.post.ActionMove.actionMove;
import static com.artifacts.api.endpoints.post.ActionRest.actionRest;
import static com.artifacts.game.logic.tasks.CompleteFightTask.completeFightTask;
import static com.artifacts.game.logic.tasks.GetFightTask.getFightTask;
import static com.artifacts.service.ConsumableManager.checkInventoryConsumables;
import static com.artifacts.service.ConsumableManager.getConsumables;
import static com.artifacts.service.UtilityEquipmentManager.*;
import static com.artifacts.service.GlobalCooldownManager.globalCooldownManager;
import static com.artifacts.controller.repositorycontroller.StoreFightResultController.storeFightResult;
import static com.artifacts.service.GlobalHealthManager.globalHealthManager;


public class Fighting {
    //fight v3.1.0
    public static void fight(String name, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask) throws InterruptedException {
        //todo spamming getCharacter here a lot. Need to call it once here, then not call it in below mentioned methods at all. Else spamming like crazy
        if (fightTask) {
            activityLocation = getFightTask(name);
        }
        equipUtilitySlotOne(name, utilityOne);
        equipUtilitySlotTwo(name, utilityTwo);
        getConsumables(name, consumable);

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
            if (checkUtilitySlotOne(name, utilityOne) || checkUtilitySlotTwo(name, utilityTwo) || !checkInventoryConsumables(name, consumable)) {
                fight(name, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                break;
            }

            response = actionFight(name);
            statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                storeFightResult(response);
                globalCooldownManager(name, response);
                globalHealthManager(name, response, consumable);
                if (fightTask) {
                    //fight response for characters v2.0.0 due to S6 update
                    var taskTotal = response.getJSONObject("data").getJSONArray("characters").getJSONObject(0).getInt("task_total");
                    var taskCurrent = response.getJSONObject("data").getJSONArray("characters").getJSONObject(0).getInt("task_progress");
//                    var taskTotal = response.getJSONObject("data").getJSONObject("character").getInt("task_total");
//                    var taskCurrent = response.getJSONObject("data").getJSONObject("character").getInt("task_progress");
                    if (taskCurrent == taskTotal) {
                        completeFightTask(name); //todo check if this works as i want. When all done i execute completeFightTask, then start fightTask again from scratch, stopping current loop
                        fight(name, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                        return;
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
                    fight(name, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                    return;

                }
                fight(name, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                return;

            } else if (statusCode == CODE_MAP_CONTENT_NOT_FOUND) {
                fight(name, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
                return;
            }
            return;
        }
    }
}