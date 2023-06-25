-- 공연장 생성
insert into venue (id, name, created_at, updated_at)
values (10001, '모나코 공연장', now(), now());

-- 공연 생성
insert into performance (venue_id, id, name, capacity, date, start_time, end_time, general_seat_price, vip_seat_price,
                         created_at, updated_at)
values (10001, 10001, '이무진 서울예대 콘서트', 1000, '2023-06-25', '20:00', '22:30', 10000, 50000, now(), now());
