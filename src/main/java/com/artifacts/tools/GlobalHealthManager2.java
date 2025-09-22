/*package com.artifacts.tools;

import org.json.JSONObject;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

//todo need to add rest and food eating in here, because it will handle all the healing in the future
public class GlobalHealthManager2 {
    public static void globalHealthManager(String name, JSONObject response) throws InterruptedException {
        var characterData = response.getJSONObject("data").getJSONObject("character");
        var maxHP = characterData.getInt("max_hp");
        var currentHP = characterData.getInt("hp") * 100;
        var unhealthy = (currentHP <= maxHP * 75); //unhealthy threshold, integer represents %
        if (unhealthy) { //todo add food eating here; also health potion usage
            response = actionRest(name);
            var statusCode = response.getInt("statusCode");
            if (statusCode == CODE_SUCCESS) {
                globalCooldownManager(name, response);
            }
        }
    }
}

 */
