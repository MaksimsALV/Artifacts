package com.artifacts;

import com.artifacts.game.engine.launcher.Login;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Instant;


import static com.artifacts.repository.GetLogsParser.getLogsParser;

public class runUpdateDatabase {
    public static void main(String[] args) throws Exception {
        final var url = "jdbc:postgresql://localhost:5432/Artifacts";
        final var user = "postgres";
        final var password = "root";

        Login.login();

        var response = getLogsParser();
        var array = (response instanceof JSONArray) ? (JSONArray) response : new JSONArray(response.toString());

        var sql = "INSERT INTO artifacts.fight_logs " +
                "(character_name, monster_name, won, turns, xp_gained, created_at) " +
                "VALUES (?, ?, ?, ?, ?, (?::timestamptz AT TIME ZONE 'UTC')) " +
                "ON CONFLICT (character_name, created_at) DO NOTHING";

        try (var connect = DriverManager.getConnection(url, user, password);
             var prepareStatement = connect.prepareStatement(sql)) {

            for (int i = 0; i < array.length(); i++) {try {
                    var object = array.getJSONObject(i);
                    prepareStatement.setString(1, object.getString("character"));
                    prepareStatement.setString(2, object.getString("monster"));
                    prepareStatement.setBoolean(3, "win".equalsIgnoreCase(object.getString("fightResult")));
                    prepareStatement.setInt(4, object.getInt("fightTurns"));
                    prepareStatement.setInt(5, object.getInt("fightExperienceGained"));
                    prepareStatement.setString(6, object.getString("createdAt"));
                    prepareStatement.executeUpdate(); // autocommit=true
                } catch (Exception ignore) {

                }
            }
        }
    }
}