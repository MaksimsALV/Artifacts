package com.artifacts.tools;

import org.json.JSONObject;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_MISSING_ITEM;
import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.items.GetItem.getItem;
import static com.artifacts.game.endpoints.mycharacters.ActionRest.actionRest;
import static com.artifacts.game.endpoints.mycharacters.ActionUseItem.actionUseItem;
import static com.artifacts.tools.GlobalCooldownManager.globalCooldownManager;

//todo need to add rest and food eating in here, because it will handle all the healing in the future
public class GlobalHealthManager3 {

    public static void globalHealthManager(String name, JSONObject response, String consumable) throws InterruptedException {
        var characterData = response.getJSONObject("data").getJSONObject("character");
        var maxHP = characterData.getInt("max_hp");
        var currentHP = characterData.getInt("hp");
        var missingHP = (maxHP - currentHP);
        var unhealthy = (currentHP < maxHP);
        if (unhealthy) {
            if (consumable == null || consumable.isEmpty()) {
                response = actionRest(name);
                int statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                }
                return;
            }
            int heal = 0;
            var healingItemEffectsData = getItem(consumable).getJSONObject("data").getJSONArray("effects");
            for (var i = 0; i < healingItemEffectsData.length(); i++) {
                var effects = healingItemEffectsData.getJSONObject(i);
                if ("heal".equals(effects.getString("code"))) {
                    heal = effects.getInt("value");
                    break;
                }
            }
            if (heal > 0) {
                int quantity = (missingHP + heal - 1) / heal; //calculation defining how many quantity to consume depending on missing health.
                response = actionUseItem(name, consumable, quantity);
                var statusCode = response.getInt("statusCode");
                if (statusCode == CODE_SUCCESS) {
                    globalCooldownManager(name, response);
                    globalHealthManager(name, response, consumable);
                } else if (statusCode == CODE_MISSING_ITEM) { //fallback to consume just 1 in case i need to eat like 5, but inventory has 4.
                    response = actionUseItem(name, consumable, 1);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                        globalHealthManager(name, response, consumable);
                    } else { //if acitonUseItem returns anything that is not 200, im just resting.
                        response = actionRest(name);
                        statusCode = response.getInt("statusCode");
                        if (statusCode == CODE_SUCCESS) {
                            globalCooldownManager(name, response);
                        }
                    }
                } else {
                    response = actionRest(name);
                    statusCode = response.getInt("statusCode");
                    if (statusCode == CODE_SUCCESS) {
                        globalCooldownManager(name, response);
                    }
                }
            }
        }
    }
}
