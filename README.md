# wanted-pre-onboarding-backend
지원자의 성명: 한준수

애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함):

[배포된 엔드포인트(Oracle Cloud Infra Structure)](http://3ja2.duckdns.org:8080/): http://3ja2.duckdns.org:8080
```
  PostMan 등 API 호출 가능한 도구 사용하여 http://3ja2.duckdns.org:8080 위치의 엔드포인트 호출
```

로컬에서 실행 또는 배포하고자 하는 경우
```
0. docker-compose 실행 가능한 환경인 경우
  1. git clone https://github.com/1-vL/wanted-pre-onboarding-backend.git
  2. cd wanted-pre-onboarding-backend
  3. docker-compose up -d
  4. 도커 컨테이너 실행 후 PostMan 등 REST API 호출 가능한 도구 사용하여 locahost:8080 등 적합한 엔드포인트 위치 호출
```

데이터베이스 테이블 구조:

![데이터베이스 테이블 구조](/src/main/resources/static/ERD.png)

```User
User
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL,
  `roles` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2dlfg6wvnxboknkp9d1h75icb` (`email`)
```
```Board
Board
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(4000) COLLATE utf8mb4_unicode_ci NOT NULL,
  `title` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `USER_ID` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKp4odxt886gskmeg165ih118md` (`USER_ID`),
  CONSTRAINT `FKp4odxt886gskmeg165ih118md` FOREIGN KEY (`USER_ID`) REFERENCES `user_tb` (`id`)

```

구현한 API의 동작을 촬영한 데모 영상 링크:

https://youtu.be/xUqpA8jYsRA

구현 방법 및 이유에 대한 간략한 설명:

시간을 두고 생각해 보면 보다 나은 구현방식이 생각 날 수도 있었겠지만, Spring JPA와 Hibernate로 빠르게 구현하는데 중점을 두고,
기본적인 구현을 마친 다음에는 우선 테스트와 배포를 하는 것을 우선하며 Docker-compose, asciidoc, swagger 등을 사용하는 쪽으로 진행했습니다.
요구사항 이외의 것은 거의 추가하지 않았고, 회원 가입 시 이메일 중복체크만 추가했습니다.

RESTful한 API를 위하여 필요한 경우 delete, put 메소드를 사용했고, 삭제의 경우 비교적 단순한 구조였고, 게시판의 게시글이 다른 서비스와 연동되어 있지 않다고 가정해서 소프트 삭제가 아닌 하드 삭제로 구현했습니다.

API 명세(request/response 포함):

[API 문서 asciidoc html (Oracle Cloud Infra Structure)](http://3ja2.duckdns.org:8080/docs/api-docs.html)

[API 문서 Swagger (Oracle Cloud Infra Structure)](http://3ja2.duckdns.org:8080/swagger-ui/index.html)


