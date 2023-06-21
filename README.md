# deepdive-ticketing-service

---

## [시스템 설계] 패키지 구조

Layer 아키텍처 자체를 나타낸 구조보다 (presentation, application, infrastructure)
Domain의 의미를 나타낼 수 있는 구조로 나타냈다 (user, venue, performance)

## [시스템 설계] Java Convention

- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) 중심으로 작성했다
- 많은 기업들이 사용하는 Package, Class, Method 네이밍 룰을 준수했다

## [시스템 설계] 초기 공연장 세팅

대부분의 경우 공연장은 초기에 있다고 가정한다
동적으로 사용자의 요청으로 이루어지는 예매와는 달리, 공연장은 초기에 건물 내 공간으로서 존재하고, 그 이후로는 변경이 거의 없다고 가정한다
따라서 data.sql로 미리 공연장에 대한 정보를 만들어 놓았다

## [Domain 설계] **공연장의 좌석**과 **공연의 좌석**
공연장에 등록된 좌석(Seat)와 **공연**과 관련된 좌석들(PerformanceSeat)을 분리했다
이유는 Seat 자체는 공연장과 연관있는 고유한 Entity이기에 몇날 몇시에 예약이 되었다는 상태값이 있으면 곤란하다  
예를 들어 1번 좌석에 6.1에 예매한 것과 6.2에 예매한 상황을 Seat Entity가 가지고 있으면 정규화가 깨진다
따라서 별도로 이력을 위한 공연좌석 Entity를 분리했다

더불어 반정규화도 진행했다, PerformanceSeat를 조회하면서 Seat를 묶어서 조회하면 성능상 문제가 생길 것이다,
그리고 실제 자연계에 존재하는 Seat 자체의 성질이 변할 염려가 적다고 생각했기에, 
Seat가 바뀜에 따라 PerformanceSeat가 바뀔 가능성 또한 적다고 생각했다

## [Domain 설계] **공연 좌석**과 **예매의 좌석**

공연 자체의 좌석은 공연 관계자가 관리하는 것이고, 예매의 좌석은 사용자가 관리한다고 가정했다
공연 관계자는 공연 좌석에 대한 값을 설정하지만, 예매한 좌석의 경우 추후 비즈니스 모델에 따라 할인 정책 등으로
개개인의 값이 달라질 수 있다
공연 좌석과 예매의 좌석 또한 다르다고 가정했다
또 공연 좌석 테이블만 있을 경우 몇시에 예매했는지에 대한 정보를 넣게 되면 정규화가 깨지게 된다
따라서 어떤 시간대에 예매를 했는지를 나타내는 예매 좌석을 분리했다

## [Domain 설계] 공연장과 공연 예매의 연관 관계

물리적인 연관 X, 예매의 경우 공연장에 대한 히스토리성 성격이 있다고 가정했다

## [시스템 설계] User ArgumentResolver

이미 인증한 사용자는 (AuthInterceptor 통과) User ArgumentResolver 를 통해서 
컨트롤러가 User 객체를 바로 파라미터로 받을 수 있게 했다 

## [시스템 설계] p6spy 를 통한 로깅 개선

p6spy 를 통해서 실제 쿼리가 어떻게 실행되는지 직관적으로 확인할 수 있다
바인딩 파라미터 등의 정보가 ?와 분리되지 않고 한곳에 모여서 보여서 편리하다

## [시스템 설계] Dockerfile 작성
local과, 실제로 테스트하는 곳의 작업 환경(DB)을 동기화하기 위해, 
mysql 기반의 Dockerfile 을 작성했다

## [Domain 설계] performance와 booking을 분리한 이유

performance (공연 예매)는 공연 관계자가 이용을 하는 API이고
booking (공연 예약)은 공연 예매를 이용하는 사용자가 이용하는 API라고 가정하여, 
도메인의 성격이 다르다고 생각했다. 따라서 분리했다

## 생략한 것

상세한 설계나 테스트를 함께하지는 못했다

- 다양한 예외 상황 테스트
- 패키지간 단방향 의존성 고려
- 예매 시간대에 대한 할인 정책 (조조 등)

## 참고

- [mozza - JVM Memory](https://medium.com/numble-it/jvm-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EA%B4%80%EB%A6%AC-57a97c1f3a82)
