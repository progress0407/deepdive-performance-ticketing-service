# deepdive-ticketing-service

---

## 패키지 구조

Layer 아키텍처 자체를 나타낸 구조보다 (presentation, application, infrastructure)
Domain의 의미를 나타낼 수 있는 구조로 나타냈다 (user, venue, performance)

## Java Convention

- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) 중심으로 작성했다
- 많은 기업들이 사용하는 Package, Class, Method 네이밍 룰을 준수했다

## 초기 공연장 세팅

대부분의 경우 공연장은 초기에 있다고 가정한다
동적으로 사용자의 요청으로 이루어지는 예매와는 달리, 공연장은 초기에 건물 내 공간으로서 존재하고, 그 이후로는 변경이 거의 없다고 가정한다
따라서 data.sql로 미리 공연장에 대한 정보를 만들어 놓았다

## **공연장의 좌석**과 공연 **예매의 좌석**
공연장에 등록된 좌석(Seat)와 공연 **예매**과 관련된 좌석들(PerformanceSeat)을 분리했다
이유는 Seat 자체는 공연장과 연관있는 고유한 Entity이기에 몇날 몇시에 예약이 되었다는 상태값이 있으면 곤란하다  
예를 들어 1번 좌석에 6.1에 예매한 것과 6.2에 예매한 상황을 Seat Entity가 가지고 있으면 정규화가 깨진다
따라서 별도로 이력을 위한 공연좌석 Entity를 분리했다

더불어 반정규화도 진행했다, PerformanceSeat를 조회하면서 Seat를 묶어서 조회하면 성능상 문제가 생길 것이다,
그리고 실제 자연계에 존재하는 Seat 자체의 성질이 변할 염려가 적다고 생각했기에, 
Seat가 바뀜에 따라 PerformanceSeat가 바뀔 가능성 또한 적다고 생각했다

## 공연장과 공연 예매의 연관 관계

물리적인 연관 X, 예매의 경우 공연장에 대한 히스토리성 성격이 있다고 가정했다

## User ArgumentResolver

이미 인증한 사용자는 (AuthInterceptor 통과) User ArgumentResolver 를 통해서 
컨트롤러가 User 객체를 바로 파라미터로 받을 수 있게 했다 

## p6spy 를 통한 로깅 개선

p6spy 를 통해서 실제 쿼리가 어떻게 실행되는지 직관적으로 확인할 수 있다
바인딩 파라미터 등의 정보가 ?와 분리되지 않고 한곳에 모여서 보여서 편리하다

## Dockerfile 작성
local과, 실제로 테스트하는 곳의 작업 환경(DB)을 동기화하기 위해, 
mysql 기반의 Dockerfile 을 작성했다

## 참고

- [mozza - JVM Memory](https://medium.com/numble-it/jvm-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EA%B4%80%EB%A6%AC-57a97c1f3a82)
