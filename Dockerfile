FROM openjdk:17-alpine

COPY build/libs/board-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

ENV TZ=Asia/Seoul
