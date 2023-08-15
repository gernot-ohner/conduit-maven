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