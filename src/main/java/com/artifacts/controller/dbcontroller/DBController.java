package com.artifacts.controller.dbcontroller;

import org.json.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class DBController {
    private final JdbcTemplate jdbc;
    public DBController(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void saveCharacterData(JSONObject data) {
        //var uuid = 1; //todo need to add UUID generator here
        //var id = 1;
        var name = data.getString("name");
        var level = data.getInt("level");

        jdbc.update("insert into logs (name, level) values (?, ?)",
                name, level);
    }
}
