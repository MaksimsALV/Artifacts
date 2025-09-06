package com.artifacts.game.logic.activity.tasks;

import com.artifacts.game.endpoints.maps.GetAllMaps;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.mycharacters.ActionAcceptNewTask.actionAcceptNewTask;
import static com.artifacts.game.endpoints.mycharacters.ActionCompleteTask.actionCompleteTask;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;
//todo maybe merge in FightingTask tbh as it is my great design template - or maybe not.
public class CompleteFightTask {
    public static void completeFightTask(String name) throws InterruptedException {
        var coordinates = GetAllMaps.getAllMaps("monsters");
        var x = coordinates.getJSONArray("data").getJSONObject(0).getInt("x");
        var y = coordinates.getJSONArray("data").getJSONObject(0).getInt("y");

        var response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }
        response = actionCompleteTask(name);
        statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }
    }
}
