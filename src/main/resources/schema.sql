CREATE TABLE IF NOT EXISTS tags
(
    id UUID PRIMARY KEY,
    tag VARCHAR NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS users
(
    id UUID PRIMARY KEY,
    username VARCHAR NOT NULL,
    token VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE ,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    bio VARCHAR,
    image VARCHAR
);

CREATE TABLE IF NOT EXISTS user_follower_relation
(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    follower_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (follower_id) REFERENCES users (id)
);