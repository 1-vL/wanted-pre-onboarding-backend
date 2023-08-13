# Dockerfile

FROM gradle:8.2.1-jdk17-alpine AS builder
WORKDIR /app

COPY ./ ./
RUN gradle clean build --no-daemon

# APP
FROM openjdk:17.0-slim

# 빌더 이미지에서 jar 파일만 복사
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar ./app.jar

EXPOSE 8080

# root 대신 nobody 권한으로 실행
USER nobody
ENTRYPOINT [                                               \
   "java",                                                 \
   "-jar",                                                 \
   "-Dspring.profiles.active=prod",                        \
   "app.jar"              \
]