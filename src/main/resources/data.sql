INSERT INTO tags(id, tag)
VALUES (gen_random_uuid(), 'foo')
ON CONFLICT DO NOTHING;

INSERT INTO users(id, email, token, username, image)
VALUES (gen_random_uuid(),
        'gernot.ohner@gmail.com',
        '{noop}password',
        'gernot',
        'https://avatars1.githubusercontent.com/u/1024025?s=460&v=4')
ON CONFLICT DO NOTHING;

INSERT INTO users(id, email, token, username, bio)
VALUES (gen_random_uuid(),
        'gernot.ohner@protonmail.com',
        '{noop}password',
        'garrett',
        'I am a software engineer')
ON CONFLICT DO NOTHING;

INSERT INTO user_follower_relation(user_id, follower_id)
VALUES ((SELECT id FROM users WHERE email = 'gernot.ohner@gmail.com'),
        (SELECT id FROM users WHERE email = 'gernot.ohner@protonmail.com'))
ON CONFLICT DO NOTHING;
