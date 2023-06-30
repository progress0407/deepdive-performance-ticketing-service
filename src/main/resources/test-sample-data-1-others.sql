insert into venue (id, name, created_at, updated_at)
values (20001, '실버스톤 공연장', now(), now());


insert into venue (id, name, created_at, updated_at)
values (20002, '모나코 공연장', now(), now());


insert into performance (venue_id, id, name, capacity,
                         date, start_time, end_time,
                         general_seat_price, vip_seat_price,
                         created_at, updated_at)
values (20001, 20001, '아이유 공연', 1000,
        '2023-06-30', '20:00', '22:30',
        10000, 50000,
        now(), now());


insert into performance (venue_id, id, name, capacity, date,
                         start_time, end_time,
                         general_seat_price, vip_seat_price,
                         created_at, updated_at)
values (20002, 20002, 'BTS 공연', 200,
        '2023-06-30', '20:00', '22:30',
        100000, 500000,
        now(), now());


insert into performance (venue_id, id, name, capacity,
                         date, start_time, end_time,
                         general_seat_price, vip_seat_price,
                         created_at, updated_at)
values (20001, 20003, '이무진 공연', 1000,
        '2023-06-25', '20:00', '22:30',
        10000, 50000,
        now(), now());
