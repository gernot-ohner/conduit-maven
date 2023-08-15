
INSERT INTO tags(id, tag) VALUES (gen_random_uuid(), 'foo');

INSERT INTO users(id, email, token, username, created_at, updated_at, bio, image)
VALUES (gen_random_uuid(),
        'gernot.ohner@gmail.com',
        '{noop}password',
        'gernot',
        now(),
        now(),
        'I work at stateless',
        'https://avatars1.githubusercontent.com/u/1024025?s=460&v=4'
        )
ON CONFLICT DO NOTHING;
