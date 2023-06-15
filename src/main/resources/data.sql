/**
  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private int capacity;

    private String venuesType; // TOOO Enum화

    private String possibleTimes;
 */
insert into venue (id, name, capacity, venues_type, possible_times)
values (1, '테스트', 100, '테스트', '테스트');

insert into seat (id, seat_number, seat_type, venue_id)
values (1, 'A1', 'VIP', 1)
     , (2, 'A2', 'VIP', 1)
     , (3, 'B1', '일반', 1)
     , (4, 'B2', '일반', 1);
