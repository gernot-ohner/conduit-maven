CREATE TABLE IF NOT EXISTS tags
(
    id  UUID    NOT NULL DEFAULT gen_random_uuid(),
    tag VARCHAR NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users
(
    id         UUID      NOT NULL DEFAULT gen_random_uuid(),
    username   VARCHAR   NOT NULL UNIQUE,
    password   VARCHAR   NOT NULL,
    email      VARCHAR   NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    bio        VARCHAR,
    image      VARCHAR,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_follower_relation
(
    id          UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    user_id     UUID      NOT NULL,
    follower_id UUID      NOT NULL,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (follower_id) REFERENCES users (id)
);