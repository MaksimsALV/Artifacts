package com.artifacts.tools;

import org.json.JSONObject;

//todo need to add rest and food eating in here, because it will handle all the healing in the future
public class GlobalHealthManager {
    public static boolean unhealthy(JSONObject response) {
        var characterData = response.getJSONObject("data").getJSONObject("character");
        var maxHP = characterData.getInt("max_hp");
        var currentHP = characterData.getInt("hp") * 100;
        var unhealthy = (currentHP <= maxHP * 75); //unhealthy threshold, integer represents %
        if (unhealthy) {
            return true;
        }
        return false;
    }
}
