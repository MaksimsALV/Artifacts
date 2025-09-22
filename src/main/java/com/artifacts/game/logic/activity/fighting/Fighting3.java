package com.artifacts.game.logic.activity.fighting;

import com.artifacts.game.endpoints.maps.GetAllMaps;
import com.artifacts.tools.GlobalHealthManager3;

import static com.artifacts.api.errorhandling.ErrorCodes.*;
import static com.artifacts.game.endpoints.mycharacters.ActionDepositBankItem.actionDepositBankItem;
import static com.artifacts.game.endpoints.mycharacters.ActionFight.actionFight;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
import static com.artifacts.game.logic.activity.tasks.CompleteFightTask.completeFightTask;
import static com.artifacts.game.logic.activity.tasks.GetFightTask.getFightTask;
import static com.artifacts.service.ConsumableManager.checkInventoryConsumables;
import static com.artifacts.service.ConsumableManager.getConsumables;
import static com.artifacts.service.UtilityEquipmentManager.*;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;
import static com.artifacts.controller.repositorycontroller.StoreFightResultController.storeFightResult;


public class Fighting3 {
    public static void
    fight(String name, String activityLocation, String utilityOne, String utilityTwo, String consumable, boolean fightTask) throws InterruptedException {
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
                storeFightResult(response); // todo to get logs in DB rolling
                globalCooldownManager(name, response);
                //GlobalHealthManager2.globalHealthManager(name, response);
                GlobalHealthManager3.globalHealthManager(name, response, consumable); //todo testing improved GHM3
                if (fightTask) {
                    var taskTotal = response.getJSONObject("data").getJSONObject("character").getInt("task_total");
                    var taskCurrent = response.getJSONObject("data").getJSONObject("character").getInt("task_progress");
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

