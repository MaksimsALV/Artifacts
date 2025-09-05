CREATE TABLE IF NOT EXISTS logs (
                                    id         BIGSERIAL PRIMARY KEY,
                                    name       TEXT        NOT NULL,
                                    level      INTEGER     NOT NULL,
                                    logged_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
    );