# 공연 예매 서비스 (동시성, 대규모 트래픽)

---

## [시스템 설계]

### 패키지 구조

Layer 아키텍처 자체를 나타낸 구조보다 (presentation, application, infrastructure)  
Domain의 의미를 나타낼 수 있는 구조로 나타냈다 (user, venue, performance)

### Java Convention

- [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) 중심으로 작성했다  
- 많은 기업들이 사용하는 Package, Class, Method 네이밍 룰을 준수했다

### 초기 공연장 세팅

대부분의 경우 공연장은 초기에 있다고 가정한다  
동적으로 사용자의 요청으로 이루어지는 예매와는 달리, 공연장은 초기에 건물 내 공간으로서 존재하고, 그 이후로는 변경이 거의 없다고 가정한다  
따라서 data.sql로 미리 공연장에 대한 정보를 만들어 놓았다  

### 클린 코드

구현보다는 의미 중심의 네이밍을 하려고 노력했다  
단 한줄의 코드라도, 의미의 명확성을 위해 메서드 추출을 했다
(마틴 파울러, 리펙터링 - 메서드 추출)

###  User ArgumentResolver

이미 인증한 사용자는 (AuthInterceptor 통과) User ArgumentResolver 를 통해서   
컨트롤러가 User 객체를 바로 파라미터로 받을 수 있게 했다 

### p6spy 를 통한 로깅 개선

p6spy 를 통해서 실제 쿼리가 어떻게 실행되는지 직관적으로 확인할 수 있다  
바인딩 파라미터 등의 정보가 ?와 분리되지 않고 한곳에 모여서 보여서 편리하다

###  Dockerfile 작성
local과, 실제로 테스트하는 곳의 작업 환경(DB)을 동기화하기 위해,  
mysql 기반의 Dockerfile 을 작성했다


## [Domain 설계]

###  performance와 booking을 분리한 이유

performance (공연 예매)는 공연 관계자가 이용을 하는 API이고  
booking (공연 예약)은 공연 예매를 이용하는 사용자가 이용하는 API라고 가정하여,  
도메인의 성격이 다르다고 생각했다. 따라서 분리했다


### **공연 좌석**과 **예매의 좌석**

공연장의 좌석은 물리적으로 존재하는 개념이다  
반면 공연의 좌석은 논리적인(가상의) 개념이다  
공연장과 공연은 1:N 관계인데, 공연장의 어느 한 좌석은 여러 개의 공연의 좌석이 될 수 있다  
즉, 같은 A1좌석일지라도 오후 1시에는 아이유 콘서트(공연)의 좌석, 오후 8시는 이무진 콘서트의 좌석이 될 수 있다  
만일 공연장 좌석과 공연 좌석을 분리하지 않으면, 물리적인 개념의 좌석에 시간 개념에 해당하는 컬럼이 들어와 정규화가 꺠지게 된다  
따라서 공연장의 좌석과 공연의 좌석을 분리했다 (Bounded Context)  
도메인 지식에 따라 공연 좌석과 예매의 좌석이 다른 경우도 있겠지만, 이 프로젝트가 가지는 비즈니스상 그럴 필요가 없다고 생각했다  


### 동시성 테스트

![image](https://github.com/progress0407/progress0407/assets/66164361/0c3a76e9-4762-4718-96bf-74b616332941)


- 동시성 고려를 하지 않은 경우
  ![image](https://github.com/progress0407/progress0407/assets/66164361/147d78b3-1a04-4772-8b81-d16df1bd8b59)

기대 예약 개수인 250개 보다 많은 366건이 예약됨을 알 수 있다

- 동시성 처리를 위해 synchronized를 사용한 경우
![image](https://github.com/progress0407/progress0407.github.io/assets/66164361/160d298f-9b2d-4fd2-a864-ce2c79ad65b2)
- 
![image](https://github.com/progress0407/progress0407/assets/66164361/9afa3829-1217-42da-9912-a116e6169743)
Connection Time Out으로 인해 몇건의 예약이 이루어지지 않았음을 알 수 있다

(그 외 사항은 [개인 블로그](https://progress0407.tistory.com/149)에 정리해두었습니다)

## 생략한 것

상세한 설계나 테스트를 함께하지는 못했다

- 다양한 예외 상황 테스트
- 패키지간 단방향 의존성 고려
- 예매 시간대에 대한 할인 정책 (조조 등)
- Spock 기반 테스트

## 참고

- [mozza - JVM Memory](https://medium.com/numble-it/jvm-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EA%B4%80%EB%A6%AC-57a97c1f3a82)
