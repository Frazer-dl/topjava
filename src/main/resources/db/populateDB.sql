DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100001, '2021-07-11 06:51:10', 'Breakfast', 2128),
       (100001, '2020-12-19 10:07:04', 'Breakfast', 416),
       (100000, '2020-11-12 05:31:33', 'Afternoon snack', 1084),
       (100000, '2021-04-15 07:37:50', 'Afternoon snack', 1907),
       (100000, '2021-03-12 10:28:08', 'Breakfast', 637),
       (100001, '2021-04-27 17:25:54', 'Lunch', 1021),
       (100001, '2020-11-06 20:52:52', 'Afternoon snack', 1016),
       (100001, '2021-03-23 13:29:11', 'Lunch', 1488),
       (100001, '2021-03-24 18:05:52', 'Breakfast', 1885),
       (100000, '2021-07-31 05:01:50', 'Dinner', 1806),
       (100000, '2021-04-02 03:11:45', 'Lunch', 2141),
       (100000, '2020-11-04 16:57:43', 'Lunch', 1516);
