package com.artifacts.controller.repositorycontroller;

import com.artifacts.game.endpoints.serverdetails.GetServerDetails;
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
        var characterData = data.getJSONObject("character");

        var monster = "unknown"; //todo new season will have opponent here

        var character = characterData.getString("name");
        var result = fightData.getString("result");
        var turns = fightData.getInt("turns");
        var xpGained = fightData.getInt("xp");
        var logsText = fightData.getJSONArray("logs").toString();
        var itemsText = fightData.getJSONArray("drops").isEmpty() ? null : fightData.getJSONArray("drops").toString();
        var gold = fightData.getInt("gold");
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
