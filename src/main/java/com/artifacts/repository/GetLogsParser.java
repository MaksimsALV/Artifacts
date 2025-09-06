package com.artifacts.repository;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.artifacts.api.errorhandling.ErrorCodes.CODE_SUCCESS;
import static com.artifacts.game.endpoints.mycharacters.GetCharacterLogs.getCharacterLogs;
import static com.artifacts.game.library.characters.Characters.getWarrior;

public class GetLogsParser {
    public static JSONArray getLogsParser() {
        var response = getCharacterLogs(getWarrior());
        var output = new JSONArray();
        var statusCode = response.getInt("statusCode");
        if (statusCode == CODE_SUCCESS) {
            var data = response.getJSONArray("data");
            for (var i = 0; i < data.length(); i++) {
                var log = data.getJSONObject(i);
                var fightLog = log.getJSONObject("content").getJSONObject("fight");
                var character = log.getString("character");
                var monster = fightLog.getString("monster");
                var fightResult = fightLog.getString("result");
                var fightTurns = fightLog.getInt("turns");
                var fightExperienceGained = fightLog.getInt("xp_gained");
                var createdAt = log.getString("created_at");
                if (!log.isEmpty()) {
                    var row = new JSONObject();
                    row.put("character", character);
                    row.put("monster", monster);
                    row.put("fightResult", fightResult);
                    row.put("fightTurns", fightTurns);
                    row.put("fightExperienceGained", fightExperienceGained);
                    row.put("createdAt", createdAt);

                }
            }
        }
        System.out.println(output);
        return output;
    }
}
