-- venue and seat
insert into venue (id, name, created_at, updated_at)
values (1, '테스트', now(), now());

insert into seat (id, seat_number, seat_type, venue_id, created_at, updated_at)
values (1, 'A1', 'VIP', 1, now(), now())
     , (2, 'A2', 'VIP', 1, now(), now())
     , (3, 'B1', 'GENERAL', 1, now(), now())
     , (4, 'B2', 'GENERAL', 1, now(), now());

-- user
insert into users(id, name, email, password, user_type, created_at, updated_at)
values (1, 'philz', 'pro@gmail.com', '1234', 'GENERAL', now(), now());

insert into general_users (id)
values (1);
