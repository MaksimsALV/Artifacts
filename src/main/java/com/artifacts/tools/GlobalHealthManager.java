package com.artifacts.tools;

import org.json.JSONObject;

public class GlobalHealthManager {
    public static boolean unhealthy(JSONObject response) {
        var characterData = response.getJSONObject("data").getJSONObject("character");
        var maxHP = characterData.getInt("max_hp");
        var currentHP = characterData.getInt("hp") * 100;
        var unhealthy = (currentHP <= maxHP * 50); //unhealthy threshold, integer represents %
        if (unhealthy) {
            return true;
        }
        return false;
    }
}
