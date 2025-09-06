package com.artifacts.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import java.time.OffsetDateTime;
import java.util.UUID;

@org.springframework.stereotype.Repository
public class Repository {
    private final JdbcTemplate jdbc;
    public Repository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public UUID insert(String character_name,
                       String monster_name,
                       boolean won,
                       int turns,
                       int xp_gained,
                       OffsetDateTime created_at) {
    var id = UUID.randomUUID();
    var sql = """
            INSERT INTO fight_logs (id, "character_name", monster_name, won, turns, xp_gained, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
    jdbc.update(sql, id, character_name, monster_name, won, turns, xp_gained, created_at);
    return id;
    }
}
