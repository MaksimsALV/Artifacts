CREATE TABLE IF NOT EXISTS artifacts.fight_logs (
                                          season         INTEGER     NOT NULL,
                                          character_name TEXT        NOT NULL,
                                          monster_name   TEXT        NOT NULL,
                                          result         TEXT        NOT NULL,
                                          turns          INTEGER     NOT NULL,
                                          xp_gained      INTEGER     NOT NULL,
                                          logs           TEXT        NOT NULL,
                                          gold           INTEGER     NOT NULL,
                                          items          TEXT,
                                          cooldown       INTEGER     NOT NULL,
                                          created_at     TIMESTAMP   NOT NULL
);