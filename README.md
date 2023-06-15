# deepdive-ticketing-service

---

공연장에 등록된 좌석(Seat)와 공연과 관련된 좌석들(PerformanceSeat)을 분리했다
이유는 Seat 자체는 공연장과 연관있는 고유한 Entity이기에 몇날 몇시에 예약이 되었다는 상태값이 있으면 곤란하다  
예를 들어 1번 좌석에 6.1에 예매한 것과 6.2에 예매한 상황을 Seat Entity가 가지고 있으면 정규화가 깨진다
따라서 별도로 이력을 위한 공연좌석 Entity를 분리했다

더불어 반정규화도 진행했다, PerformanceSeat를 조회하면서 Seat를 묶어서 조회하면 성능상 문제가 생길 것이다,
그리고 실제 자연계에 존재하는 Seat 자체의 성질이 변할 염려가 적다고 생각했기에, 
Seat가 바뀜에 따라 PerformanceSeat가 바뀔 가능성 또한 적다고 생각했다
