insert into users(id, name, email, password, user_type, created_at, updated_at)
values (1, 'test_user', 'test_user_001@numble.com', '1234', 'GENERAL', now(), now());

insert into general_users (id)
values (1);
