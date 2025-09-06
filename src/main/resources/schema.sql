CREATE TABLE IF NOT EXISTS fight_logs (
                                          id             UUID        PRIMARY KEY,
                                          character_name TEXT        NOT NULL,
                                          monster_name   TEXT        NOT NULL,
                                          won            BOOLEAN     NOT NULL,
                                          turns          INTEGER     NOT NULL,
                                          xp_gained      INTEGER     NOT NULL,
                                          created_at     TIMESTAMPTZ NOT NULL
);