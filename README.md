# my-klas
수강 신청에서 많은 트래픽을 버텨낼 수 있도록 연구하고 개선해보기 위한 토이 프로젝트입니다.
## 프로젝트 개요
* 큰 트래픽이 발생하는 수강 신청에서, 사용자 경험을 위해 응답 시간을 개선해보기 위해 진행한 개인 프로젝트입니다.
* 2000명의 학생이 6개 강의를 연달아 신청하며, 이때 인기 강의의 존재로 20%의 요청이 만석으로 인해 실패하는 상황을 가정했습니다.
* 블로그에 [개선 과정과 고민들](https://velog.io/@woodyn1002/series/my-klas)을 기록해가며 진행했습니다.
## 수행 내용
* 낙관적 락과 비관적 락을 비교하는 도중 발생한 [데드락을 MySQL의 `SHOW ENGINE` 명령어의 도움으로 해결](https://velog.io/@woodyn1002/my-klas-%EB%82%99%EA%B4%80%EC%A0%81-%EB%9D%BD-vs-%EB%B9%84%EA%B4%80%EC%A0%81-%EB%9D%BD)했습니다.
* JVM에 대한 이해를 기반으로 시뮬레이션 중 [Warm-up을 수행](https://velog.io/@woodyn1002/my-klas-%EB%B6%80%ED%95%98-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%8B%9C%EB%AE%AC%EB%A0%88%EC%9D%B4%EC%85%98)하여 평균 응답 시간을 개선했습니다. (약 1000ms 개선)
* Spring Boot Actuator로 수집한 Metrics를 시각화하여, [HikariCP 최대 풀 크기에 의한 병목을 밝혀내어 해결](https://velog.io/@woodyn1002/my-klas-%EB%B3%91%EB%AA%A9-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0)했습니다. (약 1700ms 개선)
* [MySQL Slow Query Log를 활용하여 특정 쿼리에 의한 성능 저하를 찾아](https://velog.io/@woodyn1002/my-klas-%EC%BF%BC%EB%A6%AC-%EA%B0%9C%EC%84%A0-1)내어 쿼리를 개선했습니다. (약 950ms 개선)
* [`ConcurrentHashMap`을 통해 만석 강의를 캐싱하는 로직을 작성](https://velog.io/@woodyn1002/my-klas-%EC%BF%BC%EB%A6%AC-%EA%B0%9C%EC%84%A0-2)했습니다. (약 60ms 개선)
* 2000명의 학생이 6개 강의를 동시 신청할 때, 최종 결과 평균 응답 시간 383ms로, 사용자 경험을 개선하는 데에 성공했습니다.
## 주요 기술
* Kotlin, JVM
* Spring Boot, Spring MVC
* Spring Data JPA, Hibernate
* MySQL
## 프로젝트 관련 자료
* [프로젝트 진행 회고록](https://velog.io/@woodyn1002/series/my-klas)
