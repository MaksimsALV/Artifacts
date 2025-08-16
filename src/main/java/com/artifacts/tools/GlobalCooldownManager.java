package com.artifacts.tools;

import org.json.JSONObject;

public class GlobalCooldownManager {
    public static void globalCooldownManager(String name, JSONObject response) throws InterruptedException {
        var cooldownObject = response.getJSONObject("data").getJSONObject("cooldown");
        var cooldown = cooldownObject.getInt("remaining_seconds");
        var reason = cooldownObject.getString("reason");

        if (cooldown > 0) {
            System.out.println(name + " is now on a cooldown for: " + cooldown + "s due to " + reason);
            Thread.sleep(cooldown * 1000L);
        }
    }
}
