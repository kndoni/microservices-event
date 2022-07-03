FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY  /target/microservice-event-1.0-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "/app/microservice-event-1.0-SNAPSHOT.jar"]