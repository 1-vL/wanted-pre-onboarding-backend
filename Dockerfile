FROM openjdk:17
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENV DATABASE_URL=jdbc:mysql://localhost:54132/wanted
ENTRYPOINT ["java","-jar","/app.jar"]