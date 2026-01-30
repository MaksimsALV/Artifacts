package com.artifacts.controllers.db;

import com.artifacts.api.endpoints.get.GetServerDetails;
import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class StoreFightResultController {
    private static JdbcTemplate JDBC;

    public StoreFightResultController(JdbcTemplate jdbc) {
        JDBC = jdbc;
    }

    public static void storeFightResult(JSONObject fightResponse) {
        var season = GetServerDetails.season.get("season");
        var data = fightResponse.getJSONObject("data");
        var fightData = data.getJSONObject("fight");
        var cooldownData = data.getJSONObject("cooldown");
        var fightDataForCharacter = fightData.getJSONArray("characters").getJSONObject(0);

        var character = fightDataForCharacter.getString("character_name");
        var result = fightData.getString("result");
        var monster = fightData.getString("opponent");
        var turns = fightData.getInt("turns");
        var xpGained = fightDataForCharacter.getInt("xp");
        var logsText = fightData.getJSONArray("logs").toString();
        var itemsText = fightDataForCharacter.getJSONArray("drops").isEmpty() ? null : fightDataForCharacter.getJSONArray("drops").toString();
        var gold = fightDataForCharacter.getInt("gold");
        var cooldown = cooldownData.getInt("total_seconds");
        var createdAt = OffsetDateTime.now(ZoneOffset.UTC);

        var sql =
                "INSERT INTO artifacts.fight_logs (" +
                        "  season, character_name, monster_name, result, turns, xp_gained, logs, gold, items, cooldown, created_at" +
                        ") VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        JDBC.update(sql, season, character, monster, result, turns, xpGained,
                logsText, gold, itemsText, cooldown, createdAt);
    }
}
