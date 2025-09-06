package com.artifacts.game.logic.activity.tasks;

import com.artifacts.game.endpoints.maps.GetAllMaps;
import org.json.JSONObject;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.mycharacters.ActionAcceptNewTask.actionAcceptNewTask;
import static com.artifacts.game.endpoints.mycharacters.ActionMove.actionMove;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

public class GetFightTask {
    public static String getFightTask(String name, String activityLocation) throws InterruptedException {
        var coordinates = GetAllMaps.getAllMaps(activityLocation);
        var x = coordinates.getJSONArray("data").getJSONObject(0).getInt("x");
        var y = coordinates.getJSONArray("data").getJSONObject(0).getInt("y");

        var response = actionMove(name, x, y);
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }
        response = actionAcceptNewTask(name);
        statusCode = response.getInt("statusCode");
        var taskCode = response.getJSONObject("data").getJSONObject("task").getString("code");
        if (statusCode == CODE_SUCCESS) {
            globalCooldownManager(name, response);
        }
        return taskCode;
    }
}
